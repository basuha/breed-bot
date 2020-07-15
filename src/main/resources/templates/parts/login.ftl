<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <input type="text" class="form-control" name="username" id="username" aria-describedby="emailHelp" placeholder="Как вас зовут?">
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
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
</#macro>