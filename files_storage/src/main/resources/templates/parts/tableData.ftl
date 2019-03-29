<#macro table messages isAdmin isLoad>
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
                    <th><a href="/del/${message.id}">Удалить</a></th>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</#macro>