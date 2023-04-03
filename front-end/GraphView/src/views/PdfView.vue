<template>
  <div>
    <PdfViewer :url="url" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import PdfViewer from "../components/PdfViewer.vue";

const url = ref("/pdf/中国历史常识");

function printFn() {
  window.print();
}
</script>
<style scoped></style>
