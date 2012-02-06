package trip.planner

import org.springframework.dao.DataIntegrityViolationException

class AirlineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [airlineInstanceList: Airline.list(params), airlineInstanceTotal: Airline.count()]
    }

    def create() {
        [airlineInstance: new Airline(params)]
    }

    def save() {
        def airlineInstance = new Airline(params)
        if (!airlineInstance.save(flush: true)) {
            render(view: "create", model: [airlineInstance: airlineInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'airline.label', default: 'Airline'), airlineInstance.id])
        redirect(action: "show", id: airlineInstance.id)
    }

    def show() {
        def airlineInstance = Airline.get(params.id)
        if (!airlineInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'airline.label', default: 'Airline'), params.id])
            redirect(action: "list")
            return
        }

        [airlineInstance: airlineInstance]
    }

    def edit() {
        def airlineInstance = Airline.get(params.id)
        if (!airlineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'airline.label', default: 'Airline'), params.id])
            redirect(action: "list")
            return
        }

        [airlineInstance: airlineInstance]
    }

    def update() {
        def airlineInstance = Airline.get(params.id)
        if (!airlineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'airline.label', default: 'Airline'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (airlineInstance.version > version) {
                airlineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'airline.label', default: 'Airline')] as Object[],
                          "Another user has updated this Airline while you were editing")
                render(view: "edit", model: [airlineInstance: airlineInstance])
                return
            }
        }

        airlineInstance.properties = params

        if (!airlineInstance.save(flush: true)) {
            render(view: "edit", model: [airlineInstance: airlineInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'airline.label', default: 'Airline'), airlineInstance.id])
        redirect(action: "show", id: airlineInstance.id)
    }

    def delete() {
        def airlineInstance = Airline.get(params.id)
        if (!airlineInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'airline.label', default: 'Airline'), params.id])
            redirect(action: "list")
            return
        }

        try {
            airlineInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'airline.label', default: 'Airline'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'airline.label', default: 'Airline'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
