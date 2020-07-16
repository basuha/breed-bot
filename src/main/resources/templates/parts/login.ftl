<#macro login path isRegisterForm>
    <div class="container-md mx-auto" style="width: 50%;">
        <div class="card card-body">
            <#if !isRegisterForm>
                <h5 class="card-title">Log in</h5>
            <#else>
                <h5 class="card-title">Registration</h5>
            </#if>
            <form action="${path}" method="post">
                <div class="form-group">
                    <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')}"
                           value="<#if user??>${user.username}</#if>"
                           name="username" id="username" placeholder="What`s your name?">
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control ${(passwordError??)?string('is-invalid','')}"
                           value="<#if user??>${user.password}</#if>"
                           name="password" id="password" placeholder="Enter password...">
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
                <div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </div>
                <#if !isRegisterForm>
                    <button type="submit" class="btn btn-primary">Sign in</button>
                    <#else>
                        <button type="submit" class="btn btn-warning">Sign up</button>
                </#if>
                <#if !isRegisterForm>
                    <a class="btn btn-warning" href="/register" role="button">Sing up</a>
                </#if>
            </form>
        </div>

    </div>
</#macro>