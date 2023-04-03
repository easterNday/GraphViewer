<!-- TypeScript语言的代码 -->
<script lang="ts" setup>
// 引入Vue的ref响应式引用
import { ref } from "vue";
import PreviewGraph from "@/components/PreviewGraph.vue";

// 引入Element Plus图标组件库里的Search图标
import { Search } from "@element-plus/icons-vue";

// 声明一个ref类型的变量input，初始值为空字符串
const input = ref("");
const filterInput = ref("");

const setFilter = () => (filterInput.value = input.value);
</script>

<!-- HTML模板 -->
<template>
  <div class="graph">
    <el-card class="box-card">
      <template #header>
        <el-row>
          <el-col :span="20">
            <!-- 搜索输入框，使用双向绑定将输入的关键字保存到input变量中 -->
            <el-input
              class="dark"
              v-model="input"
              placeholder="请输入你要查询的关键字……（例如：秦始皇）"
              size="large"
              :suffix-icon="Search"
              @keyup.enter="setFilter"
            />
          </el-col>
        </el-row>
      </template>
      <div style="width: 50vh; height: 50vh; background-color: black">
        <PreviewGraph :input="filterInput" />
      </div>
    </el-card>
  </div>
</template>

<!-- CSS样式 -->
<style>
@media (min-width: 1024px) {
  .graph {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
