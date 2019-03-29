<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    ${message!}
    <form method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <@l.register user false/>
        <button type="submit" formaction="/registration" class="btn btn-primary">Зарегестрироваться</button>
    </form>
</@c.page>