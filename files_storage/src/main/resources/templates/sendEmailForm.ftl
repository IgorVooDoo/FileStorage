<#import "parts/common.ftl" as c>

<@c.page>
    ${message!}
    <form method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group  row">
            <label class="col-sm-2 col-form-label">E-mail:</label>
            <div class="col-sm-6">
                <label>
                    <input
                            type="email" name="email"
                            value="${email!}"
                            class="form-control"
                            placeholder="email@example.com">
                </label>

            </div>
        </div>

        <button type="submit" formaction="/sendCode" class="btn btn-primary">Отправить</button>
    </form>
</@c.page>