<script setup lang="ts">
import WelcomeItem from "./WelcomeItem.vue";
import DocumentationIcon from "./icons/IconDocumentation.vue";
import ToolingIcon from "./icons/IconTooling.vue";
import EcosystemIcon from "./icons/IconEcosystem.vue";
import CommunityIcon from "./icons/IconCommunity.vue";
import SupportIcon from "./icons/IconSupport.vue";
</script>

<template>
  <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>文档</template>

    Vue的
    <a href="https://vuejs.org/" target="_blank" rel="noopener"
      >官方文档</a
    >
    为你提供了开始使用Vue所需的所有信息。
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>工具</template>

    该项目使用
    <a
      href="https://vitejs.dev/guide/features.html"
      target="_blank"
      rel="noopener"
      >Vite</a
    > 提供服务和打包。推荐使用的IDE设置是
    <a href="https://code.visualstudio.com/" target="_blank" rel="noopener"
      >VSCode</a
    >
    +
    <a
      href="https://github.com/johnsoncodehk/volar"
      target="_blank"
      rel="noopener"
      >Volar</a
    >。如果您需要测试组件和页面，请查看
    <a href="https://www.cypress.io/" target="_blank" rel="noopener">Cypress</a>
    和
    <a href="https://on.cypress.io/component" target="_blank"
      >Cypress组件测试</a
    >。

    <br />

    更多说明请参见<code>README.md</code>。
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>生态</template>

    获取项目的官方工具和库：
    <a href="https://pinia.vuejs.org/" target="_blank" rel="noopener">Pinia</a>
    、
    <a href="https://router.vuejs.org/" target="_blank" rel="noopener"
      >Vue Router</a
    >、
    <a href="https://test-utils.vuejs.org/" target="_blank" rel="noopener"
      >Vue Test Utils</a
    >和
    <a href="https://github.com/vuejs/devtools" target="_blank" rel="noopener"
      >Vue Dev Tools</a
    >。如果您需要更多资源，我们建议访问
    <a
      href="https://github.com/vuejs/awesome-vue"
      target="_blank"
      rel="noopener"
      >Awesome Vue</a
    >。
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>社区</template>

    遇到问题了吗？在
    <a href="https://chat.vuejs.org" target="_blank" rel="noopener">Vue Land</a
    >（我们的官方Discord服务器）或
    <a
      href="https://stackoverflow.com/questions/tagged/vue.js"
      target="_blank"
      rel="noopener"
      >StackOverflow</a
    >上提问。您还应该订阅
    <a href="https://news.vuejs.org" target="_blank" rel="noopener"
      >我们的邮件列表</a
    >
    和关注官方
    <a href="https://twitter.com/vuejs" target="_blank" rel="noopener"
      >@vuejs</a
    >
    推特帐户，了解Vue世界的最新消息。
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>支持Vue</template>

    作为一个独立的项目，Vue依赖于社区来支持其可持续性。您可以通过
    <a href="https://vuejs.org/sponsor/" target="_blank" rel="noopener"
      >成为赞助商</a
    >来帮助我们。
  </WelcomeItem>
</template>