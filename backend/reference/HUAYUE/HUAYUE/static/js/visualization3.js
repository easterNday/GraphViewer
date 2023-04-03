//立即执行函数
(function() {
	var myChart = echarts.init(document.querySelector("#tab2"));
	var option = {
		title: {
			text: "“壮族”关联实体三元组数量",
			top: 'bottom',
			left: 'right'
		},
		color: ["#3398D8"],
		tooltip: {
			trigger: "axis",
			axisPointer: {
				type: "shadow"
			}
		},
		toolbox: {
			// 显示工具箱
			show: true,
			feature: {
				mark: {
					show: true
				},
				// 保存为图片
				saveAsImage: {
					show: true
				}
			}
		},

		xAxis: {
			type: 'category',
			data: ['农产品', '壮族人', '牛魂节', '壮锦', '桂林山水', '铜鼓', '广西壮族自治区']
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			type: "bar",
			// data:[JSON.stringify(my_data.dataKg2)]
			data: [3, 10, 1, 1, 1, 1, 1]
		}]
	};
	myChart.setOption(option);
	window.addEventListener("resize", function() {
		myChart.resize();
	})
})();

(function() {
	var myChart = echarts.init(document.querySelector("#tab1"));
	var option;
	myChart.showLoading();
	// ROOT_PATH + '/data/asset/data/les-miserables.json'
	//使用ajax，后期可以从前端获取数据，传入qData里,数据格式如qdata,目前已访问成功
	 var qdata={"skip":1,"n1":"","n2":"","n3":"","n4":"","n5":"","n6":"","r1":"","r2":"","r3":"","r4":"","r5":"","limit":4};
	  $.ajax({
	 url:"http://127.0.0.1:8000/run/",
	 type:"post",
	 timeout:50000,
	 async:false,
	 data:JSON.stringify(qdata),
	 dataType:"json",
	 contentType:"application/json",
	 success:function(result,status,xhr){
		 var Rdata=JSON.stringify(result);//这是原本传回的数据，后续要判断是否有skip\limit错误等
		 n=JSON.stringify(result.res.nodes);//nodes
		 r=JSON.stringify(result.res.links);//links
		 c=JSON.stringify(result.res.categories);//categories
	     //alert("数据: \n" + result + "\n状态: " + status+"\n这个？:"+xhr);
	     //这里依次将它弹窗显示
	     //alert("json字符："+Rdata);
	     //alert(n);
	     //alert(r);
	     //alert(c);
	     myChart.hideLoading();
		 console.log(result.res.nodes);
		 // graph.nodes.forEach(function(node) {
		 // 	node.label = {
		 // 		show: node.symbolSize > 30
		 // 	};
		 // });
		 option = {
			 title: {
				text: '知识图谱',
				subtext: 'Default layout',
				top: 'bottom',
				left: 'right'
			 },
			 tooltip: {
				 formatter: function(x) {
				 	return x.data.name;
				 },
			 },
			 toolbox: {
				// 显示工具箱
				show: true,
				feature: {
					mark: {
						show: true
					},
					// 还原
					restore: {
						show: true
					},
					// 保存为图片
					saveAsImage: {
						show: true
					}
				}
			 },
			 legend: [{
				// selectedMode: 'single',
				orient:'vertical',
				x:'left',
				padding:[15,0,0,0],
				data: result.res.categories.map(function(a) {
					return a.name;
				})
			 }],
			 animationDuration: 1500,
			 animationEasingUpdate: 'quinticInOut',
			 series: [{
				 // name: '知识图谱',
				 data: result.res.nodes,
				 links: result.res.links,
				 categories: result.res.categories,

				 type: 'graph',
				 layout: 'force',
				 force: {
					 repulsion: 200,
					 edgeLength: 120,
				 },
				 // circular: {
				 // 	rotateLabel: true
				 // },
				 // symbolSize: 40, // 调整节点的大小
				 roam: true,
				 draggable:true,
				 edgeSymbol: ['circle', 'arrow'],
				 focusNodeAdjacency:true,// 关系、节点聚焦
				 itemStyle: {
					 borderColor: '#fff',
					 borderWidth: 3,
					 shadowBlur: 10,
					 shadowColor: 'rgba(0, 0, 0, 0.3)'
				 },
				 edgeLabel: {
					 show: true,
					 position:'middle',
					 formatter: function(x) {
						 return x.data.name;
					 },
					 fontSize:16,
				 },

				 label: {
					 normal: {
						 show: true,
						 position: 'inside',
						 formatter: '{b}',
						 fontSize: 12,
						 fontStyle: '400',
					 }
				 },

				 // label: {
				 // 	position: 'right',
				 // 	formatter: '{b}'
				 // },
				 // 高亮+关系边样式
				 lineStyle: {
					 normal:{
						 width:2,
						 curveness:0.3,    // 边的曲度
						 opacity:0.7,
						 color:'target',   // 边的颜色是与起点（source）相同还是与终点（target）相同
					 }
				 },
				 emphasis: {
					 scale: true,
					 focus: 'adjacency',
					 lineStyle: {
						 width: 10
					 }
				 }
			 }]
		 };
		 myChart.setOption(option);
		 window.addEventListener("resize", function() {
			 myChart.resize();
		 })
	     },
	 error:function(xhr,status,error){
	 	alert("错误: \n" + error + "\n状态: " + status+"\n这个？:"+xhr.status);
	 }
	 });
})();

function search(skip,n1,r1,n2,r2,n3,r3,n4,r4,n5,r5,n6,limit){
    //alert(skip+n1+r1+n2+r2+n3+r3+n4+r4+n5+r5+n6+limit);
    var myChart = echarts.init(document.querySelector("#tab1"));
	var option;
	myChart.showLoading();
	// ROOT_PATH + '/data/asset/data/les-miserables.json'
	//使用ajax，后期可以从前端获取数据，传入qData里,数据格式如qdata,目前已访问成功
	var qdata={"skip":skip,"n1":n1,"n2":n2,"n3":n3,"n4":n4,"n5":n5,"n6":n6,"r1":r1,"r2":r2,"r3":r3,"r4":r4,"r5":r5,"limit":Number(limit)};
	$.ajax({
	    url:"http://127.0.0.1:8000/run/",
	    type:"post",
	    timeout:50000,
	    async:false,
	    data:JSON.stringify(qdata),
	    dataType:"json",
	    contentType:"application/json",
	    success:function(result,status,xhr){
            var Rdata=JSON.stringify(result);//这是原本传回的数据，后续要判断是否有skip\limit错误等
            n=JSON.stringify(result.res.nodes);//nodes
            r=JSON.stringify(result.res.links);//links
            c=JSON.stringify(result.res.categories);//categories
	        //alert("数据: \n" + result + "\n状态: " + status+"\n这个？:"+xhr);
	        //这里依次将它弹窗显示
	        //alert("json字符："+Rdata);
	        //alert(n);
	        //alert(r);
	        //alert(c);
			//这是整个符合mock.json文件格式的数据给kg0，没成功
	        //kg0=JSON.stringify(result.res);
	        myChart.hideLoading();
		    console.log(result.res.nodes);

            // graph.nodes.forEach(function(node) {
            // 	node.label = {
            // 		show: node.symbolSize > 30
            // 	};
            // });
            option = {
                title: {
                    text: '知识图谱',
                    subtext: 'Default layout',
                    top: 'bottom',
                    left: 'right'
                },
                tooltip: {
                     formatter: function(x) {
                        return x.data.name;
                     },
                },
                toolbox: {
                    // 显示工具箱
                    show: true,
                    feature: {
                        mark: {
                            show: true
                        },
                        // 还原
                        restore: {
                            show: true
                        },
                        // 保存为图片
                        saveAsImage: {
                            show: true
                        }
                    }
                },

                legend: [{
                    // selectedMode: 'single',
                    orient:'vertical',
                    x:'left',
                    padding:[15,0,0,0],
                    data: result.res.categories.map(function(a) {
                        return a.name;
                    })
                }],
                animationDuration: 1500,
                animationEasingUpdate: 'quinticInOut',
                series: [{
                    // name: '知识图谱',
                    data: result.res.nodes,
                    links: result.res.links,
                    categories: result.res.categories,

                    type: 'graph',
                    layout: 'force',
                    force: {
                        repulsion: 200,
                        edgeLength: 120,
                    },
                    // circular: {
                    // 	rotateLabel: true
                    // },
                    // symbolSize: 40, // 调整节点的大小
                    roam: true,
                    draggable:true,
                    edgeSymbol: ['circle', 'arrow'],
                    // 关系、节点聚焦：
                    focusNodeAdjacency:true,
                    itemStyle: {
                        borderColor: '#fff',
                        borderWidth: 3,
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.3)'
                    },
                    edgeLabel: {
                        show: true,
                        position:'middle',
                        formatter: function(x) {
                            return x.data.name;
                        },
                        fontSize:16,
                                },

                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                            formatter: '{b}',
                            fontSize: 12,
                            fontStyle: '400',
                        }
                    },

                    // label: {
                    // 	position: 'right',
                    // 	formatter: '{b}'
                    // },
                    // 高亮+关系边样式
                    lineStyle: {
                        normal:{
                            width:2,
                            curveness:0.3,    // 边的曲度
                            opacity:0.7,
                            color:'target',   // 边的颜色是与起点（source）相同还是与终点（target）相同
                        }
                    },
                    emphasis: {
                        scale: true,
                        focus: 'adjacency',
                        lineStyle: {
                            width: 10
                        }
                    }
                }]
            };
		    myChart.setOption(option);
		    window.addEventListener("resize", function() {
			    myChart.resize();
		    })
	        },
	    error:function(xhr,status,error){
	 	    alert("错误: \n" + error + "\n状态: " + status+"\n这个？:"+xhr.status);
	    }
	});
}

(function() {
	var myChart = echarts.init(document.querySelector("#tab3"));
	var option;
	option = {
		title: {
			text: "民族篇关联实体占比",
			top: 'bottom',
			left: 'right'
		},
		toolbox: {
			show: true,
			feature: {
				mark: {
					show: true
				},
				dataView: {
					show: true,
					readOnly: false
				},
				restore: {
					show: true
				},
				saveAsImage: {
					show: true
				}
			}
		},
		tooltip: {
			trigger: 'item',
			formatter: '{b} : {c} ({d}%)'
		},
		series: [{
			name: '',
			type: 'pie',
			// radius: [50, 250],  大小
			center: ['50%', '50%'],
			roseType: 'area',
			itemStyle: {
				borderRadius: 8
			},
			// data:[JSON.stringify(my_data.dataKg3)]
			data: [{
					value: 33,
					name: '中华民族'
				},
				{
					value: 15,
					name: '汉族'
				},
				{
					value: 10,
					name: '满族'
				},
				{
					value: 8,
					name: '回族'
				},
				{
					value: 8,
					name: '苗族'
				},
				{
					value: 10,
					name: '维吾尔族'
				},
				{
					value: 17,
					name: '彝族'
				},
				{
					value: 19,
					name: '蒙古族'
				},
				{
					value: 15,
					name: '藏族'
				},
				{
					value: 19,
					name: '朝鲜族'
				},
				{
					value: 14,
					name: '傣族'
				},
				{
					value: 9,
					name: '高山族'
				}
			]
		}]
	};
	myChart.setOption(option);
	window.addEventListener("resize", function() {
		myChart.resize();
	})
})();
