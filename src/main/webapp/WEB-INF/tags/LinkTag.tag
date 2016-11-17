<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="pageComputer" scope="application" type="com.excilys.formation.pagination.Page" />
<footer class="navbar-fixed-bottom">
<c:if test="$(pageComputer.currentPage > pageComputer.nbPages)">
	
</c:if>
        <div class="container text-center">
            <ul class="pagination">
                <li>
	                <c:if test= "${pageComputer.currentPage != 1}">
	                    <a href="?page=${pageComputer.currentPage - 1}&perPage=${pageComputer.elementsByPage}" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                  </a>
	                </c:if>
		            </li>
		            <c:if test = "${pageComputer.currentPage > 2}">
		            <li><a href="?page=${pageComputer.currentPage - 2}&perPage=${pageComputer.elementsByPage}">${pageComputer.currentPage - 2}</a></li>
		            </c:if>
		            <c:if test = "${pageComputer.currentPage > 1}">
		            <li><a href="?page=${pageComputer.currentPage - 1}&perPage=${pageComputer.elementsByPage}">${pageComputer.currentPage - 1}</a></li>
		            </c:if>
		            <li><a href="?page=${pageComputer.currentPage}&perPage=${pageComputer.elementsByPage}">${pageComputer.currentPage}</a></li>
		            <c:if test= "${pageComputer.currentPage < pageComputer.nbPages }">
		            <li><a href="?page=${pageComputer.currentPage + 1}&perPage=${pageComputer.elementsByPage}">${pageComputer.currentPage + 1}</a></li>
		            </c:if>
		            <c:if test= "${pageComputer.currentPage < pageComputer.nbPages-1 }">
		            <li><a href="?page=${pageComputer.currentPage + 2}&perPage=${pageComputer.elementsByPage}">${pageComputer.currentPage + 2}</a></li>
		            </c:if>
		            <li>
		            <c:if test= "${pageComputer.currentPage != pageComputer.nbPages}" > 
		              <a href="?page=${pageComputer.currentPage + 1}&perPage=${pageComputer.elementsByPage}" aria-label="Next">
		                  <span aria-hidden="true">&raquo;</span>
		              </a>
	             	</c:if>
            	</li>
        	</ul>
		
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default" onclick="location.href='?page=1&perPage=10'">10</button>
            <button type="button" class="btn btn-default" onclick="location.href='?page=1&perPage=50'">50</button>
            <button type="button" class="btn btn-default" onclick="location.href='?page=1&perPage=100'">100</button>
        </div>
        </div>

    </footer>