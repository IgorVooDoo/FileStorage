<#import "parts/common.ftl" as c>
<#import "parts/accessForm.ftl" as acc>

<@c.page>
    <h4 class="text-center mt-3">Пользователи открывшие доступ для скачивания</h4>
    <@acc.access loadOwner false true/>

</@c.page>