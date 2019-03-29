<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<#import "parts/tableData.ftl" as t>

<@c.page>
    <form method="post">
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <@l.register user true/>

        <#list roles as role>
            <div class="form-check mt-3">
                <input id="${role}" class="form-check-input" type="checkbox"
                       name="${role}" ${user.roles?seq_contains(role)?string("checked","")}>
                <label for="${role}" class="form-check-label">${role}</label>
            </div>
        </#list>

        <button type="submit" formaction="/user/save" class="btn btn-primary mt-3">Сохранить</button>
        <button type="submit" formaction="/user/del" class="btn btn-primary mt-3">Удалить</button>
    </form>
    <hr/>
    <h4 class="text-center mt-3">Список файлов</h4>

    <@t.table messages true true/>

</@c.page>