<%@ page isErrorPage="true" %>  
  
<h3>Sorry an exception occured!</h3>  
  
<%
    if(request.getAttribute("error") != null) { %>
        Exception is: <%= request.getAttribute("error") %>
    <% } else { %>
        Exception is: <%= exception %> 
    <% }
%>