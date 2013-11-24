

<%@ page import="bank.Transaction" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'transaction.label', default: 'Transaction')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="userTransactions"><g:message code="user.transactions" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${transactionInstance}">
            <div class="errors">
                <g:renderErrors bean="${transactionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="from"><g:message code="transaction.from.label" default="From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: transactionInstance, field: 'from', 'errors')}">
                                    <g:select name="from" from="${bank.Account.list()}" optionKey="id" value="${transactionInstance?.from}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="to"><g:message code="transaction.to.label" default="To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: transactionInstance, field: 'to', 'errors')}">
                                    <g:select name="to" from="${bank.Account.list()}" optionKey="id" value="${transactionInstance?.to}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="amount"><g:message code="transaction.amount.label" default="Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: transactionInstance, field: 'amount', 'errors')}">
                                    <g:textField name="amount" value="${fieldValue(bean: transactionInstance, field: 'amount')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
