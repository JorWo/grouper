<%@ include file="../assetsJsp/commonTaglib.jsp"%>

                <form id="groupQuerySortAscendingFormId">
                  <input type="hidden" name="querySortAscending" value="${grouperRequestContainer.groupContainer.guiSorting.ascending}" /> 
                </form> 

                <table class="table table-hover table-bordered table-striped table-condensed data-table">
                  <thead>
                    <tr>
                      <th class="${grouperRequestContainer.groupContainer.guiSorting.columnCssClass['lastUpdatedDb']}"
                         onclick="ajax('../app/UiV2Group.viewAuditsFilter?groupId=${grouperRequestContainer.groupContainer.guiGroup.group.id}&querySortAscending=${!grouperRequestContainer.groupContainer.guiSorting.ascending}', {formIds: 'groupFilterAuditFormId,groupPagingAuditFormId'}); return false;"
                         >${textContainer.text['groupAuditLogFilterColumnDate']}</th>
                      <th>${textContainer.text['groupAuditLogFilterColumnActor']}</th>
                      <th>${textContainer.text['groupAuditLogFilterColumnEngine']}</th>
                      <th>${textContainer.text['groupAuditLogFilterColumnSummary']}</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${grouperRequestContainer.groupContainer.guiAuditEntries}" 
                      var="guiAuditEntry" >
                      <c:set var="auditEntry" value="${guiAuditEntry.auditEntry}" />
                      <%--
                      <tr>
                        <td>18 Dec 2012 12:30:45</td>
                        <td><a href="#">Jane Smith</td>
                        <td>grouperUI</td>
                        <td><strong>Assigned membership</strong><br /><a href="#">Bob Smith</a> was added as an <em>immediate member</em> to the <em>members list</em> of <a href="#">Editors.</td>
                      </tr>
                      --%>
                      <tr>
                        <td>${guiAuditEntry.guiDate}</td>
                        <td>${guiAuditEntry.guiSubjectPerformedAction.shortLinkWithIcon}</td>
                        <td>${guiAuditEntry.grouperEngineLabel}</td>
                        <td>${guiAuditEntry.auditLine }</td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
                <div class="data-table-bottom gradient-background">
                  <grouper:paging2 guiPaging="${grouperRequestContainer.groupContainer.guiPaging}" formName="groupPagingAuditForm" ajaxFormIds="groupFilterAuditFormId, groupQuerySortAscendingFormId"
                    refreshOperation="../app/UiV2Group.viewAuditsFilter?groupId=${grouperRequestContainer.groupContainer.guiGroup.group.id}" />
                </div>

