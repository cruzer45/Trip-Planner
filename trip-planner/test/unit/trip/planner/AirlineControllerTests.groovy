package trip.planner



import org.junit.*
import grails.test.mixin.*

@TestFor(AirlineController)
@Mock(Airline)
class AirlineControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/airline/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.airlineInstanceList.size() == 0
        assert model.airlineInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.airlineInstance != null
    }

    void testSave() {
        controller.save()

        assert model.airlineInstance != null
        assert view == '/airline/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/airline/show/1'
        assert controller.flash.message != null
        assert Airline.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/airline/list'


        populateValidParams(params)
        def airline = new Airline(params)

        assert airline.save() != null

        params.id = airline.id

        def model = controller.show()

        assert model.airlineInstance == airline
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/airline/list'


        populateValidParams(params)
        def airline = new Airline(params)

        assert airline.save() != null

        params.id = airline.id

        def model = controller.edit()

        assert model.airlineInstance == airline
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/airline/list'

        response.reset()


        populateValidParams(params)
        def airline = new Airline(params)

        assert airline.save() != null

        // test invalid parameters in update
        params.id = airline.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/airline/edit"
        assert model.airlineInstance != null

        airline.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/airline/show/$airline.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        airline.clearErrors()

        populateValidParams(params)
        params.id = airline.id
        params.version = -1
        controller.update()

        assert view == "/airline/edit"
        assert model.airlineInstance != null
        assert model.airlineInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/airline/list'

        response.reset()

        populateValidParams(params)
        def airline = new Airline(params)

        assert airline.save() != null
        assert Airline.count() == 1

        params.id = airline.id

        controller.delete()

        assert Airline.count() == 0
        assert Airline.get(airline.id) == null
        assert response.redirectedUrl == '/airline/list'
    }
}
