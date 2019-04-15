<#macro access listUsers isAdmin isLoad>
    <#import "tableData.ftl" as t>
    <div class="accordion " id="accordionExample">
        <#list listUsers as item>
            <div class="card">
                <div class="card-header" id="idOwner${item.id}">
                    <h2 class="mb-0">
                        <button class="btn btn-link  collapsed" type="button" data-toggle="collapse"
                                data-target="#${item.username}" aria-controls="${item.username}">${item.username} ( ${item.dataObjects?size} )</button>
                    </h2>
                </div>

                <div id="${item.username}" class="collapse" aria-labelledby="headingOne"
                     data-parent="#accordionExample">
                    <div class="card-body">
                        <@t.table messages=item.dataObjects isAdmin=isAdmin isLoad=isLoad/>
                    </div>
                </div>
            </div>
        <#else>
            <h4 class="text-center mt-3">Доступы не предоставлены</h4>
        </#list>
    </div>

</#macro>

<#macro members listUsers isAdmin isQuery>
    <form method="get">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Имя</th>
                <#if isAdmin>
                    <th scope="col">Роль</th>
                </#if>
                <th scope="col">Действие</th>
            </tr>
            </thead>
            <tbody>
            <#list listUsers as member>
                <tr>
                    <th><label name="mem">${member.username}</label></th>
                    <#if isAdmin>
                        <th><#list member.roles as role>${role}<#sep>, </#list></th>
                        <th><a href="/user/${member.id}">Редактировать</a></th>
                    <#else>
                        <#if isQuery>
                            <th><a href="/member/add/${member.id}">Принять</a></th>
                            <th><a href="/member/del/${member.id}">Отклонить</a></th>
                        <#else>
                            <th><a href="/member/del/${member.id}">Отвязать</a></th>
                        </#if>
                    </#if>
                </tr>
            </#list>
            </tbody>

        </table>
    </form>
</#macro>