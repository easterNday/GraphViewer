<template>
  <div>
    <PdfViewer :url="url" />
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import PdfViewer from "../components/PdfViewer.vue";

const url = ref<string>("/pdf/中国历史常识");

function printFn(): void {
  window.print();
}
</script>

<style scoped></style>
