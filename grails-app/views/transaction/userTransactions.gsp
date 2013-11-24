
<%@ page import="bank.Transaction" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'transaction.label', default: 'Transaction')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="pay"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            </div>
            <div class="body">
                <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>

            <g:form>
                <g:select name="accountId" from="${bank.Account.list()}" optionKey="id" />
                <g:submitButton name="display" value="Display Account Transactions" />
            </g:form>

            <br />

            <g:if test="${transactionInstanceList}">

                <div class="list">
                    <table>
                        <thead>
                            <tr>

                                <th><g:message code="transaction.from.label" default="From" /></th>

                                <th><g:message code="transaction.to.label" default="To" /></th>

                                    <g:sortableColumn property="amount" title="${message(code: 'transaction.amount.label', default: 'Amount')}" />

                                    <g:sortableColumn property="dateCreated" title="${message(code: 'transaction.dateCreated.label', default: 'Date Created')}" />

                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${transactionInstanceList}" status="i" var="transactionInstance">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                                    <td>${fieldValue(bean: transactionInstance, field: "from")}</td>

                                    <td>${fieldValue(bean: transactionInstance, field: "to")}</td>

                                    <td>${fieldValue(bean: transactionInstance, field: "amount")}</td>

                                    <td><g:formatDate date="${transactionInstance.dateCreated}" /></td>

                                </tr>
                            </g:each>
                        </tbody>
                    </table>
                </div>
                <div class="paginateButtons">
                    <g:paginate total="${transactionInstanceTotal}" />
                </div>
            </div>
        </g:if>
    </body>
</html>
