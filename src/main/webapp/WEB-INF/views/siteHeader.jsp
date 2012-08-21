<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>



<div id="header_container">
	<div id="masthead">
		<div id="header_controls">
			<div class="headerInline">
				<span class="headerLeft">
					<ul>
						<li>
							<a href="<c:url value='/'/>"><img src="<c:url value='/resources/images/headerlogosmall.png'/>" alt="Flutterby" /></a>
						</li>
					</ul>
				</span>
				<span class="headerRight">
					<sec:authorize access="isAuthenticated()">
						<ul>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<li>
									<a href="<c:url value='/admin/settings'/>"><img src="<c:url value='/resources/images/settingsBtn.png'/>" alt="Settings" align="left"></a>
								</li>
							</sec:authorize>
							<li>
								<tags:dropdown dropdownId="worktask" loadFunction='/notification/getTrackerNotifications.ajax' title="Work Ticket" alt="Work Ticket" imageSource="/resources/images/workTicketBtn.png" altImageSource="/resources/images/workTicketBtnNew.png"><tiles:insertTemplate template="/WEB-INF/views/dashboardMenus/messageDash.jsp" /></tags:dropdown>
							</li>
							<li>
								<tags:dropdown dropdownId="question" loadFunction='/notification/getQuestionNotifications.ajax' title="Question" alt="Question" imageSource="/resources/images/questionBtn.png" altImageSource="/resources/images/questionBtnNew.png"><tiles:insertTemplate template="/WEB-INF/views/dashboardMenus/messageDash.jsp" /></tags:dropdown>
							</li>
							<li>
								<tags:dropdown dropdownId="todo" loadFunction='/notification/getTodoNotifications.ajax' title="Todo List" alt="Todo List" imageSource="/resources/images/todoBtn.png" altImageSource="/resources/images/todoBtnNew.png"><tiles:insertTemplate template="/WEB-INF/views/dashboardMenus/messageDash.jsp"/></tags:dropdown>
							</li>
							<li>
								<tags:dropdown dropdownId="email" loadFunction='/notification/getEmailNotifications.ajax' title="Email" alt="Email" imageSource="/resources/images/email.png" altImageSource="/resources/images/emailNew.png"><tiles:insertTemplate template="/WEB-INF/views/dashboardMenus/messageDash.jsp"/></tags:dropdown>
							</li>
							<li>
								<a href="#"><img id="peopleSearch" src="<c:url value='/resources/images/people.png'/>" alt="People Directory" title="People Directory" align="left"/></a>
							</li>
						</ul>
					</sec:authorize>
				</span>
			</div>
		</div>
	</div>			
</div>


<!--  People finder popup window -->
<tags:popup popupId="people" handler="peopleSearch" loadFunction="loadPeopleFinder" loadArgs="url">
	 <h1>People Finder</h1>  
    <div id="peopleDirectory"></div>  
</tags:popup>


<!--  create ToDo popup window -->
<div id="todoPopup" class="popupWindow">  
    <a id="todoPopupClose" class="popupWindowClose">x</a>  
    <h1>Create New ToDo</h1>  
    <div id="todoContent">
    	<tiles:insertTemplate template="/WEB-INF/views/createTodo.jsp" />
    </div>  
</div>  
<div id="todoBackground" class="popupBackground"></div> 

<!--  create Question popup window -->
<div id="questionPopup" class="popupWindow">  
    <a id="questionPopupClose" class="popupWindowClose">x</a>  
    <h1>Ask a Question</h1>  
    <div id="questionContent">
    	<tiles:insertTemplate template="/WEB-INF/views/createQuestion.jsp" />
    </div>  
</div>  
<div id="questionBackground" class="popupBackground"></div> 

<!--  create work ticket popup window -->
<div id="workTicketPopup" class="popupWindow">  
    <a id="workTicketPopupClose" class="popupWindowClose">x</a>  
    <h1>Raise Work Request</h1>  
    <div id="workTicketContent">
    	<tiles:insertTemplate template="/WEB-INF/views/createWorkTicket.jsp" />
    </div>  
</div>  
<div id="workTicketBackground" class="popupBackground"></div> 

<!--  create message popup window -->
<div id="msgPopup" class="popupWindow">  
    <a id="msgPopupClose" class="popupWindowClose">x</a>  
    <h1>Send a Message</h1>  
    <div id="msgContent">
    	<tiles:insertTemplate template="/WEB-INF/views/emailCompose.jsp" />
    </div>  
</div>  
<div id="msgBackground" class="popupBackground"></div> 