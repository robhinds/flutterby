<%@ tag language="java" pageEncoding="ISO-8859-1" description="display a dropdown populated with contacts (teams/people)"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='j' uri='http://www.thirdmindmedia.co.uk/butterfly/jsonarray' %>

<%@ attribute name='identifier' required="true" description='identifier for code' %>

<select name="contact" id="${identifier}ContactId" size="1" class="autoWidth">
 	<option></option>
	<optgroup label="Teams">
		<j:forEachJson items="${contactsTeams}" var="contact">
			<option value="<j:jsonGetString key="id" json="${contact}" />">
				<j:jsonGetString key="name" json="${contact}" />
			</option>
		</j:forEachJson>
	</optgroup>
	<optgroup label="People">
		<j:forEachJson items="${contactsPersons}" var="contact">
			<option value="<j:jsonGetString key="id" json="${contact}" />">
				<j:jsonGetString key="name" json="${contact}" />
			</option>
		</j:forEachJson>
	</optgroup>
</select> 