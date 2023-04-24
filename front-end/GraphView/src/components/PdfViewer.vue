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
      style="justify-content: center"
    />
    <img ref="imgRef" :src="currentPageUrl" style="width: 100%" />
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

const props = defineProps({
  url: {
    type: String,
    required: true,
  },
});

const currentPage = ref(1);
const numPages = ref(0);
const dialogVisible = ref(false);
const graphInput = ref("");
const currentPageUrl = ref("");
const imgRef = ref(null);

const handleClose = (done: () => void) => {
  ElMessageBox.confirm("你确定要关闭当前对话框吗?")
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};

const renderImg = async () => {
  var response = await axiosInstance.get(`${props.url}/pagecount`);
  numPages.value = response.data;
  console.log(response.data);
  response = await axiosInstance.get(`${props.url}_${currentPage.value}`, {
    responseType: "arraybuffer",
  });
  const imgData = new Uint8Array(response.data);
  const blob = new Blob([imgData], { type: "image/jpeg" });
  currentPageUrl.value = URL.createObjectURL(blob);
};

const handleCurrentPageChange = (newPage: number) => {
  currentPage.value = newPage;
  renderImg();
};

const handleTagClick = (tag: string) => {
  graphInput.value = tag;
  dialogVisible.value = true;
};
onMounted(renderImg);
</script>

<style scoped>
canvas {
  border: 1px solid black;
  direction: ltr;
}
</style>
