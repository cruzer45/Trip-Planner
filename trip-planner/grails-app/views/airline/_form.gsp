<%@ page import="trip.planner.Airline" %>



<div class="fieldcontain ${hasErrors(bean: airlineInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="airline.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="100" required="" value="${airlineInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: airlineInstance, field: 'url', 'error')} ">
	<label for="url">
		<g:message code="airline.url.label" default="Url" />
		
	</label>
	<g:field type="url" name="url" value="${airlineInstance?.url}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: airlineInstance, field: 'frequentFlyer', 'error')} ">
	<label for="frequentFlyer">
		<g:message code="airline.frequentFlyer.label" default="Frequent Flyer" />
		
	</label>
	<g:textField name="frequentFlyer" value="${airlineInstance?.frequentFlyer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: airlineInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="airline.notes.label" default="Notes" />
		
	</label>
	<g:textArea name="notes" cols="40" rows="5" maxlength="1500" value="${airlineInstance?.notes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: airlineInstance, field: 'trip', 'error')} ">
	<label for="trip">
		<g:message code="airline.trip.label" default="Trip" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${airlineInstance?.trip?}" var="t">
    <li><g:link controller="trip" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="trip" action="create" params="['airline.id': airlineInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'trip.label', default: 'Trip')])}</g:link>
</li>
</ul>

</div>

