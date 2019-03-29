<#import "parts/common.ftl" as c>
<#import "parts/accessForm.ftl" as acc>

<@c.page>
    <h4 class="text-center mt-3 col-sm-8">Статистика пользователей</h4>
    <div class="form-group row">
        <label class="col-sm-4 col-form-label">Количество пользователей : </label>
        <div class="col-sm-4">
            <label>
                <input type="text" name="usersCount" value="${usersCount}" class="form-control" disabled/>
            </label>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-4 col-form-label">Количество сообщений : </label>
        <div class="col-sm-4">
            <label>
                <input type="text" name="messagesCount" value="${messagesCount}" disabled class="form-control">
            </label>
        </div>
    </div>

    <@acc.access users false true/>

</@c.page>