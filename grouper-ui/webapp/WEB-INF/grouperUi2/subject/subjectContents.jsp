<%@ include file="../assetsJsp/commonTaglib.jsp"%>
                <table class="table table-hover table-bordered table-striped table-condensed data-table table-bulk-update footable">
                  <thead>
                    <tr>
                      <td colspan="5" class="table-toolbar gradient-background"><a href="#" onclick="ajax('../app/UiV2Subject.removeGroups?subjectId=${grouperRequestContainer.subjectContainer.guiSubject.subject.id}&sourceId=${grouperRequestContainer.subjectContainer.guiSubject.subject.sourceId}', {formIds: 'groupFilterFormId,groupPagingFormId,membersToDeleteFormId'}); return false;" class="btn">${textContainer.text['subjectRemoveSelectedGroupsButton'] }</a></td>
                    </tr>
                    <tr>
                      <th>
                        <label class="checkbox checkbox-no-padding">
                          <input type="checkbox" name="notImportantXyzName" id="notImportantXyzId" onchange="$('.membershipCheckbox').prop('checked', $('#notImportantXyzId').prop('checked'));" />
                        </label>
                      </th>
                      <th data-hide="phone,medium">${textContainer.text['subjectMembershipStemColumn']}</th>
                      <th>${textContainer.text['subjectMembershipGroupColumn']}</th>
                      <th data-hide="phone">${textContainer.text['subjectMembershipMembershipColumn']}</th>
                      <th style="width:100px;"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <form id="membersToDeleteFormId">
                      <c:set var="i" value="0" />
                      <c:forEach items="${grouperRequestContainer.subjectContainer.guiMembershipSubjectContainers}" 
                        var="guiMembershipSubjectContainer" >
                        <c:set var="guiMembershipContainer" value="${guiMembershipSubjectContainer.guiMembershipContainers['members']}" />
                        <tr>
                          <td>
                            <label class="checkbox checkbox-no-padding">
                              <c:choose>
                                <c:when test="${guiMembershipContainer.membershipContainer.membershipAssignType.immediate}">
                                  <input type="checkbox" name="membershipRow_${i}" value="${guiMembershipContainer.membershipContainer.immediateMembership.uuid}" class="membershipCheckbox" />
                                </c:when>
                                <c:otherwise>
                                  <input type="checkbox" disabled="disabled"/>
                                </c:otherwise>
                              </c:choose>
                            </label>
                          </td>
                          <td class="expand foo-clicker">${guiMembershipSubjectContainer.guiGroup.parentGuiStem.shortLinkWithIcon} <br/>
                          <td class="expand foo-clicker">${guiMembershipSubjectContainer.guiGroup.shortLinkWithIcon} <br/>
                          </td>
                          <td data-hide="phone">
                            ${textContainer.text[grouper:concat2('groupMembershipAssignType_',guiMembershipContainer.membershipContainer.membershipAssignType)] }
                          </td>
                          <td>
                            <div class="btn-group"><a data-toggle="dropdown" href="#" class="btn btn-mini dropdown-toggle">${textContainer.text['groupViewActionsButton'] } <span class="caret"></span></a>
                              <ul class="dropdown-menu dropdown-menu-right">
                                <li><a href="edit-person-membership.html">${textContainer.text['groupViewEditMembershipsAndPrivilegesButton'] }</a></li>
                                <c:if test="${guiMembershipContainer.membershipContainer.membershipAssignType.immediate && guiMembershipSubjectContainer.guiGroup.hasUpdate }">
                                  <li><a href="#" onclick="ajax('../app/UiV2Subject.removeGroup?subjectId=${grouperRequestContainer.subjectContainer.guiSubject.subject.id}&sourceId=${grouperRequestContainer.subjectContainer.guiSubject.subject.sourceId}&groupId=${guiMembershipSubjectContainer.guiGroup.group.uuid}', {formIds: 'groupFilterFormId,groupPagingFormId'}); return false;" class="actions-revoke-membership">${textContainer.text['subjectViewRevokeMembershipButton'] }</a></li>
                                </c:if>
                                <c:if test="${guiMembershipContainer.membershipContainer.membershipAssignType.nonImmediate}">
                                  <li><a href="#"  onclick="return guiV2link('operation=UiV2Membership.traceMembership&groupId=${guiMembershipSubjectContainer.guiGroup.group.id}&memberId=${guiMembershipSubjectContainer.guiMember.member.uuid}&field=members&backTo=subject'); return false;" class="actions-revoke-membership">${textContainer.text['groupViewTraceMembershipButton'] }</a></li>
                                </c:if>
                                <c:if test="${guiMembershipSubjectContainer.guiSubject.group}">
                                  <li><a href="#" onclick="return guiV2link('operation=UiV2Group.viewGroup&groupId=${guiMembershipSubjectContainer.guiSubject.subject.id}');">${textContainer.text['groupViewViewGroupButton'] }</a></li>
                                </c:if>
                              </ul>
                            </div>
                          </td>
                        </tr>
                        <c:set var="i" value="${i+1}" />
                      </c:forEach>
                    </form>
                  </tbody>
                </table>
                <div class="data-table-bottom gradient-background">
                  <grouper:paging2 guiPaging="${grouperRequestContainer.subjectContainer.guiPaging}" formName="groupPagingForm" ajaxFormIds="groupFilterFormId"
                    refreshOperation="../app/UiV2Subject.filter?subjectId=${grouperRequestContainer.subjectContainer.guiSubject.subject.id}&sourceId=${grouperRequestContainer.subjectContainer.guiSubject.subject.sourceId}" />
                </div>