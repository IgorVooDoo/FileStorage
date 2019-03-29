<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>

    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            Неверное имя пользователя или пароль,<br/> или необходимо произвести активацию учетной записи.<br/>
            Для повторной отправки кода перейдите по <a href="/sendCode">ссылке</a>.

        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>



    <@l.login "/login" false/>

</@c.page>