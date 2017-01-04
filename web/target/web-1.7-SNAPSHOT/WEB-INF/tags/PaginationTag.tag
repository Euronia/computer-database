<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<c:forEach items="${pageComputer.elements}" var="a">
			<tr>
				<td class="editMode">
                    <input type="checkbox" name="cb" class="cb" value="${a.id}">
                </td>
				<td>
					<a href="/computerdatabase/editComputer?id=${a.id}" onclick="">${a.name}</a>
				</td>
				<td>
					<c:if test="${a.introduced != null}" > 
						${a.introduced}
					</c:if>
				</td>		
				<td>
					<c:if test="${a.discontinued != null}" > 
						${a.discontinued}
					</c:if>
				</td>
				<td>
					<c:if test="${a.companyName != null}" > 
						${a.companyName}
					</c:if>
				</td>
			</tr>
		</c:forEach>
