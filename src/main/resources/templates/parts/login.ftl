<#macro login path isRegisterForm>
    <div class="container-md mx-auto" style="width: 50%;">
        <div class="card card-body">
            <#if !isRegisterForm>
                <h5 class="card-title">Вход</h5>
            <#else>
                <h5 class="card-title">Регистрация</h5>
            </#if>
            <form action="${path}" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="username" id="username" placeholder="Как вас зовут?">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" id="password" placeholder="Введите пароль...">
                </div>
                <div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </div>
                <#if !isRegisterForm>
                    <button type="submit" class="btn btn-primary">Войти</button>
                    <#else>
                        <button type="submit" class="btn btn-warning">Зарегистрироваться</button>
                </#if>
                <#if !isRegisterForm>
                    <a class="btn btn-warning" href="/register" role="button">Новый пользователь</a>
                </#if>
            </form>
        </div>

    </div>
</#macro>