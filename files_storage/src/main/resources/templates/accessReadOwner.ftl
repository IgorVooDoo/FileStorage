<#import "parts/common.ftl" as c>
<#import "parts/accessForm.ftl" as acc>

<@c.page>
    <h4 class="text-center mt-3">Пользователи открывшие доступ для чтения</h4>
    <@acc.access readOwner false false/>

</@c.page>