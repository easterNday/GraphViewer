<template>
  <relation-graph
    ref="graphRef"
    :options="options"
    :on-node-click="onNodeClick"
  />
</template>

<script lang="ts" setup>
import RelationGraph from "relation-graph/vue3";
import axiosInstance from "@/router/axios";
import { ref, onMounted, watch } from "vue";

const props = defineProps({
  input: {
    type: String,
    required: true,
  },
});

const graphRef = ref<RelationGraph>();

const options = {
  moveToCenterWhenRefresh: false,
  zoomToFitWhenRefresh: false,
  backgrounImageNoRepeat: true,
  layouts: [
    {
      label: "自动布局",
      layoutName: "force",
      layoutClassName: "seeks-layout-center",
      defaultExpandHolderPosition: "hide",
      defaultJunctionPoint: "border",
    },
  ],
};

const loadJsonData = async (input: string) => {
  const response = await axiosInstance.get(`/api/${input}`);
  return response.data;
};

const onNodeClick = async (node: any) => {
  if (node.color === "#131313") {
    const jsonData = await loadJsonData(node.id);
    graphRef.value?.setJsonData(jsonData);
  }
};

onMounted(async () => {
  const jsonData = await loadJsonData(props.input);
  graphRef.value?.setJsonData(jsonData, (graphInstance) => {
    // 这些写上当图谱初始化完成后需要执行的代码
    graphInstance.refresh();
  });
});

watch(
  () => props.input,
  async (newValue, oldValue) => {
    const jsonData = await loadJsonData(newValue);
    graphRef.value?.setJsonData(jsonData, (graphInstance) => {
      // 这些写上当图谱初始化完成后需要执行的代码
      graphInstance.refresh();
    });
  }
);
</script>

<style>
.seeks-layout-center,
.rel-map {
  background-color: #181818 !important;
}
</style>
