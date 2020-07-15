<#macro login path isRegisterForm>
    <div class="container-md">

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
        <button type="submit" class="btn btn-primary">Войти</button>
        <#if !isRegisterForm>
            <a href="/register">Add new user</a>
        </#if>
    </form>

    </div>
</#macro>