<template>
  <div>
    <el-pagination
      small
      background
      layout="jumper, prev, pager, next"
      v-model:page-count="numPages"
      v-model:total="numPages"
      :current-page="currentPage"
      @current-change="handleCurrentPageChange"
      :style="{ width: `${viewportWidth}px` }"
      style="justify-content: center"
    />
    <canvas ref="canvasRef"></canvas>
    <el-row
      :style="{ width: `${viewportWidth}px` }"
      :justify="'center'"
      :align="'middle'"
      :gutter="20"
    >
      <el-col
        v-for="(tag, index) in jsonData[currentPage]"
        :key="index"
        :span="
          jsonData[currentPage]?.length <= 4
            ? 24 / jsonData[currentPage]?.length
            : index % 4 === 0
            ? 6
            : 5
        "
        style="display: flex; justify-content: center; align-items: center"
      >
        <el-tag :type="tag.class" @click="handleTagClick(tag)">{{
          tag
        }}</el-tag>
      </el-col>
    </el-row>
  </div>
  <el-dialog
    v-model="dialogVisible"
    title="图谱展示"
    width="80%"
    :before-close="handleClose"
  >
    <template #footer>
      <div style="height: 60vh">
        <RelationGraph :input="graphInput" ref="relationGraphRef" />
      </div>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import { ElMessageBox } from "element-plus";
import axiosInstance from "@/router/axios";
import RelationGraph from "@/components/PreviewGraph.vue";
const pdfjsLib = window["pdfjs-dist/build/pdf"];

pdfjsLib.GlobalWorkerOptions.workerSrc =
  "//mozilla.github.io/pdf.js/build/pdf.worker.js";

const props = defineProps({
  url: {
    type: String,
    required: true,
  },
});

const canvasRef = ref(null);
const currentPage = ref(1);
const numPages = ref(0);
const viewportWidth = ref(0);
const jsonData = ref([]);
const dialogVisible = ref(false);
const graphInput = ref("");

const handleClose = (done: () => void) => {
  ElMessageBox.confirm("Are you sure to close this dialog?")
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};

const fetchPdf = async () => {
  const response = await axiosInstance.get(props.url, {
    responseType: "arraybuffer",
  });
  const data = new Uint8Array(response.data);

  // 读取前缀，解析 JSON 数据和 PDF 数据
  const prefixBytes = data.slice(0, 4);
  const jsonBytes = data.slice(
    4,
    4 + new DataView(prefixBytes.buffer).getInt32()
  );
  const pdfBytes = data.slice(4 + jsonBytes.length);

  const pdfDoc = await pdfjsLib.getDocument(pdfBytes).promise;
  jsonData.value = JSON.parse(new TextDecoder().decode(jsonBytes));

  return pdfDoc;
};

const renderPdf = async () => {
  const canvas = canvasRef.value;
  const ctx = canvas.getContext("2d");
  const pdfDoc = await fetchPdf();
  const page = await pdfDoc.getPage(currentPage.value);
  const viewport = page.getViewport({ scale: 1 });
  canvas.width = viewport.width;
  canvas.height = viewport.height;
  viewportWidth.value = viewport.width;
  await page.render({ canvasContext: ctx, viewport });
  numPages.value = pdfDoc.numPages;

  // 处理 JSON 数据
  console.log(jsonData.value[currentPage.value]);
};

const handleCurrentPageChange = (newPage: number) => {
  currentPage.value = newPage;
  renderPdf();
};

const handleTagClick = (tag: string) => {
  graphInput.value = tag;
  dialogVisible.value = true;
};

onMounted(renderPdf);
</script>

<style scoped>
canvas {
  border: 1px solid black;
  direction: ltr;
}
</style>
