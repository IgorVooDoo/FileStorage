<#import "parts/common.ftl" as c>
<#import "parts/tableData.ftl" as t>


<@c.page>
    <#if message?? && messageType??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseAddFile" role="button" aria-expanded="false"
       aria-controls="collapseAddFile">
        Добавить файл
    </a>

    <div class="collapse" id="collapseAddFile">
        <div class="form-group mt-3">
            <form method="post" action="/addData" enctype="multipart/form-data">
                <div class="form-group">
                    <label>
                        <input type="text" name="name" placeholder="Введите название" class="form-control"/>
                    </label>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="customFile" required="true"/>
                        <label class="custom-file-label" for="customFile">Выберите файл</label>
                        <div class="invalid-feedback">Обязательный элемент</div>
                    </div>
                </div>
                <div class="form-group">
                    <button id="but2" type="submit" class="btn btn-primary">Добавить</button>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </div>
    </div>

    <hr/>

    <h4 class="text-center mt-3">Список загруженных файлов</h4>
        <@t.table messages true true/>
    <hr/>

</@c.page>