<#import "parts/common.ftl" as c>
<#import "parts/accessForm.ftl" as acc>

<@c.page>
    <p>
        <a class="btn btn-primary col-sm-4" data-toggle="collapse" href="#collapse1" role="button" aria-expanded="false"
           aria-controls="collapse1">
            Предоставить доступ
        </a>
        <a class="btn btn-primary col-sm-4" data-toggle="collapse" href="#collapse2" role="button" aria-expanded="false"
           aria-controls="collapse2">
            Запросить доступ
        </a>
    </p>
    <div class="row">
        <div class="col col-sm-4">
            <div class="collapse multi-collapse" id="collapse1">
                <div class="form-group">
                    <form name="addMembers" method="post">
                        <input type="hidden" value="${_csrf.token}" name="_csrf">
                        <div class="row ml-1 mb-3 col-sm-15">
                            <label>
                                <select name="mem" class="custom-select">
                                    <#list users as mem>
                                        <option value="${mem.id}" name="mem">${mem.username}</option>
                                    </#list>
                                </select>
                            </label>
                        </div>
                        <div class="row  ml-1  mb-3  col-sm-15">
                            <button id="butRead" class="btn btn-success" type="submit" formaction="/member/addRead">
                                Добавить на чтение
                            </button>
                        </div>
                        <div class="row  ml-1  mb-3  col-sm-15">
                            <button id="butLoad" class="btn btn-success" type="submit" formaction="/member/addLoad">
                                Добавить на скачивание
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col col-sm-4">
            <div class="collapse multi-collapse" id="collapse2">
                <div class="form-group">
                    <form name="queryMembers" method="post">
                        <#--noinspection FtlReferencesInspection-->
                        <input type="hidden" value="${_csrf.token}" name="_csrf">
                        <div class="row ml-1 mb-3 col-sm-15">
                            <label>
                                <select name="mem" class="custom-select">
                                    <#list usersQuery as mem>
                                        <option value="${mem.id}" name="mem">${mem.username}</option>
                                    </#list>
                                </select>
                            </label>
                        </div>
                        <div class="row ml-1 mb-3 col-sm-15">
                            <button id="butQueryRead" class="btn btn-success" type="submit"
                                    formaction="/member/queryRead">Запрос на чтение
                            </button>
                        </div>
                        <div class="row ml-1 mb-3 col-sm-15">
                            <button id="butQueryLoad" class="btn btn-success" type="submit"
                                    formaction="/member/queryLoad">Запрос на скачивание
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>

    <hr/>

    <div class="accordion" id="accordionExample">
        <div class="card">
            <div class="card-header" id="first">
                <h2 class="mb-0">
                    <button class="btn btn-link  collapsed" type="button" data-toggle="collapse"
                            data-target="#readAccess" aria-controls="readAccess">
                        Открыт доступ на чтение ( ${readMember?size} )
                    </button>
                </h2>
            </div>

            <div id="readAccess" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div class="card-body">
                    <@acc.members listUsers=readMember isAdmin=false isQuery=false/>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header" id="second">
                <h2 class="mb-0">
                    <button class="btn btn-link  collapsed" type="button" data-toggle="collapse"
                            data-target="#loadAccess" aria-controls="loadAccess">
                        Открыт доступ на скачивание ( ${loadMember?size} )
                    </button>
                </h2>
            </div>

            <div id="loadAccess" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div class="card-body">
                    <@acc.members listUsers=loadMember isAdmin=false isQuery=false/>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header" id="query1">
                <h2 class="mb-0">
                    <button class="btn btn-link  collapsed" type="button" data-toggle="collapse"
                            data-target="#readQuery" aria-controls="readQuery">
                        Запрос доступа на чтение ( ${readQueryMember?size} )
                    </button>
                </h2>
            </div>

            <div id="readQuery" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div class="card-body">
                    <@acc.members listUsers=readQueryMember isAdmin=false isQuery=true/>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header" id="query2">
                <h2 class="mb-0">
                    <button class="btn btn-link  collapsed" type="button" data-toggle="collapse"
                            data-target="#loadQuery" aria-controls="loadQuery">
                        Запрос доступа на скачивание ( ${loadQueryMember?size} )
                    </button>
                </h2>
            </div>

            <div id="loadQuery" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div class="card-body">
                    <@acc.members listUsers=loadQueryMember isAdmin=false isQuery=true/>
                </div>
            </div>
        </div>
    </div>



</@c.page>