<#include "security.ftl">
<#import "login.ftl" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Home</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if isAdmin >
                <li class="nav-item">
                    <a class="nav-link" href="/user">Пользователи</a>
                </li>
            <#elseif isAnalyst >
                <li class="nav-item">
                    <a class="nav-link" href="/analyst">Статистика</a>
                </li>
            <#else>
                <#if user?? >
                    <li class="nav-item">
                        <a class="nav-link" href="/accessReadOwner">Для просмотра</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/accessLoadOwner">Для скачивания</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/accessMember">Доступы</a>
                    </li>
                </#if>
            </#if>
        </ul>
        <div class="navbar-text mr-3">${name}</div>
        <#if user?? >
            <@l.logout />
        </#if>
    </div>
</nav>