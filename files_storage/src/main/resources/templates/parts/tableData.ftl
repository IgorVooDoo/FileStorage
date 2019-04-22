<#macro table messages isAdmin isLoad>

<form>
    <table class="table table-striped">
        <thead class="thead-light">
        <tr>
            <th scope="col">Название</th>
            <th scope="col">Тип</th>
            <th scope="col">Кол-во скачиваний</th>
            <#if isLoad>
                <th scope="col">Скачать</th>
            </#if>
            <#if isAdmin>
                <th scope="col">Удалить</th>
            </#if>
        </tr>
        </thead>

        <tbody>
        <#list messages as message>
            <tr id="${message.id}">
                <th scope="row">${message.name!}</th>
                <th scope="row">${message.contentType!}</th>
                <th scope="row">${message.accessCount!}</th>
                <#if isLoad>
                    <th><a href="/download/${message.id}">Скачать</a></th>
                </#if>
                <#if isAdmin>
                    <th>
                        <button type="submit" class="btn btn-primary" onclick="deleteData(${message.id})">Удалить</button>
                    </th>
                </#if>
            </tr>
        </#list>
        </tbody>

    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>

</#macro>