<#macro login path isRegisterForm>

    <h4 class="text-center mt-3">
        Введите логин и пароль
    </h4>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пользователь:</label>
            <div class="col-sm-6">
                <label>
                    <input type="text" name="username" class="form-control"/>
                </label>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-6">
                <label>
                    <input type="text" name="password" class="form-control"/>
                </label>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Добавить пользовтаеля</a></#if>
        <button type="submit" class="btn btn-primary">
            <#if isRegisterForm>Создать<#else>Войти</#if>
        </button>
    </form>
</#macro>

<#macro register user isAdmin>
    <h4 class="text-center mt-3">
        <#if !isAdmin>Добавление пользователя
        <#else>Редактирование данных пользователя
        </#if>
    </h4>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Пользователь:</label>
        <div class="col-sm-6">
            <label>
                <input
                        type="text"
                        name="username"
                        value="${user.username!}"
                        class="form-control ${(usernameError??)?string('is-invalid','')}"
                        placeholder="Имя пользователя"/>
            </label>
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
    </div>
    <#if !isAdmin>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-6">
                <label>
                    <input
                            type="password"
                            name="password"

                            class="form-control ${(passwordError??)?string('is-invalid','')}"
                            placeholder="Пароль">
                </label>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Подтверждение:</label>
            <div class="col-sm-6">
                <label>
                    <input
                            type="password"
                            name="password2"
                            class="form-control ${(password2Error??)?string('is-invalid','')}"
                            placeholder="Пароль">
                </label>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
        </div>
    </#if>
    <div class="form-group  row">
        <label class="col-sm-2 col-form-label">E-mail:</label>
        <div class="col-sm-6">
            <label>
                <input
                        type="email" name="email"
                        value="${user.email!}"
                        class="form-control ${(emailError??)?string('is-invalid','')}"
                        placeholder="email@example.com">
            </label>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group  row">
        <label class="col-sm-2 col-form-label">Номер телефона:</label>
        <div class="col-sm-6">
            <label>
                <input type="tel" name="phone" value="${user.phone!}" class="form-control">
            </label>
        </div>
    </div>
</#macro>



<#macro logout>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-primary">Выйти</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>