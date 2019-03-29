<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<#import "parts/accessForm.ftl" as acc>

<@c.page>
    <h4 class="text-center mt-3">Список пользователей</h4>

    <@acc.members listUsers=users isAdmin=true isQuery=false/>

</@c.page>
