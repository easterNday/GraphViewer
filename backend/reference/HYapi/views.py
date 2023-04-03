from django.shortcuts import render

# Create your views here.
from django.http import JsonResponse, HttpResponseNotAllowed, HttpResponse, response
from django.views.decorators.csrf import csrf_exempt
from rest_framework import status
from py2neo import Graph, Node, Relationship, NodeMatcher, RelationshipMatcher
import re
import json
import rest_framework.parsers
from rest_framework.parsers import JSONParser



@csrf_exempt
def run(request):
    # 判断请求头是否为json
    #request.AddHeader("Access-Control-Allow-Origin", "*");

    if request.content_type != 'application/json':
        # 如果不是的话，返回405
        return HttpResponse('only support json data',status=status.HTTP_415_UNSUPPORTED_MEDIA_TYPE)
    if request.method == 'POST':
        try:
            data = JSONParser().parse(request)
            print(data)
            skip = data["skip"]
            n1 = ''
            if(data["n1"]!=""):
                n1="n1:"+data["n1"]
            n2 = ''
            if (data["n2"] != ""):
                n2 = "n2:" + data["n2"]
            n3 = ''
            if (data["n3"] != ""):
                n3 = "n3:" + data["n3"]
            n4 = ''
            if (data["n4"] != ""):
                n4 = "n4:" + data["n4"]
            n5 = ''
            if (data["n5"] != ""):
                n5 = "n5:" + data["n5"]
            n6 = ''
            if (data["n6"] != ""):
                n6 = "n6:" + data["n6"]
            r1 = ''
            if (data["r1"] != ""):
                r1 = "r1:" + data["r1"]
            r2 = ''
            if (data["r2"] != ""):
                r2 = "r2:" + data["r2"]
            r3 = ''
            if (data["r3"] != ""):
                r3 = "r3:" + data["r3"]
            r4 = ''
            if (data["r4"] != ""):
                r4 = "r4:" + data["r4"]
            r5 = ''
            if (data["r5"] != ""):
                r5 = "r5:" + data["r5"]
            limit = data['limit']
            if limit < 1:  # 限制三元组个数不能小于1个
                print("error")
                content={'res':'limit error'}
                return JsonResponse(data=content, status=status.HTTP_200_OK)
            else:
                limit = 'limit ' + str(limit)
                #暂时要在这里改成本地的图数据库地址，并连接
                graph = Graph('http://127.0.0.1:7474/', auth=('neo4j', '123456'))  #原来是'http://124.71.57.62:7474/'
                if skip == 0:
                    kg_data = graph.run("MATCH p=(" + n1 + ") return p " + limit).data()
                elif skip == 1:
                    kg_data = graph.run("MATCH p=(" + n1 + ")-[" + r1 + "]->(" + n2 + ") return p " + limit).data()
                elif skip == 2:
                    kg_data = graph.run(
                        "MATCH p= (" + n1 + ")-[" + r1 + "]->(" + n2 + ")-[" + r2 + "]->(" + n3 + ") return p " + limit).data()
                elif skip == 3:
                    kg_data = graph.run(
                        "MATCH p= (" + n1 + ")-[" + r1 + "]->(" + n2 + ")-[" + r2 + "]->(" + n3 + ")-[" + r3 + "]->(" + n4 + ") return p " + limit).data()
                elif skip == 4:
                    kg_data = graph.run(
                        "MATCH p= (" + n1 + ")-[" + r1 + "]->(" + n2 + ")-[" + r2 + "]->(" + n3 + ")-[" + r3 + "]->(" + n4 + ")-[" + r4 + "]->(" + n5 + ") return p " + limit).data()
                elif skip == 5:
                    kg_data = graph.run(
                        "MATCH p= (" + n1 + ")-[" + r1 + "]->(" + n2 + ")-[" + r2 + "]->(" + n3 + ")-[" + r3 + "]->(" + n4 + ")-[" + r4 + "]->(" + n5 + ")-[" + r5 + "]->(" + n6 + ") return p " + limit).data()
                else:
                    print('error')
                    content={'res':'skip error'}
                    return JsonResponse(data=content, status=status.HTTP_200_OK)
                print(kg_data)
                # 初始化
                kg_node = []
                kg_links = []
                kg_categories = []
                id_num = 0 #自增id的展示
                n_set=set() #存储节点名称集合
                n_dict={} #存储节点名称与id之间的映射

                # 提取括号中的字符串——节点名称和节点的关系
                Rnode = re.compile(r'[(](.*?)[)]', re.S)  # 最小匹配
                Rrel = re.compile(r'[:](.*?)[{]', re.S)  # 最小匹配

                for i in kg_data:
                    kgnode = re.findall(Rnode, str(i['p']))
                    kgrel = re.findall(Rrel, str(i["p"]))
                    print(i['p'])
                    print(kgnode)
                    print(kgrel)
                    # 将字符串添加入kg_node中.并在kg_link作出关系指定,categories中加入关系
                    if skip == 0:
                        kg_node.append({"id": str(id_num), "name": kgnode[0], "symbolSize": 30, "category": id_num})
                        kg_links.append({"name": "", "source": "", "target": ""})
                        kg_categories.append({"name": ""})
                        id_num = id_num + 1
                    elif skip >= 1 and skip <= 5:
                        for j in range(0,len(kgnode),1):
                            if kgnode[j] not in n_set:

                                # 如果，该节点名称不在集合中，正常赋予id,并绑定节点与id
                                n_set.add(kgnode[j])
                                n_dict[kgnode[j]]=str(id_num)
                                kg_node.append({"id": str(id_num), "name": kgnode[j], "symbolSize": 30, "category": id_num})
                                id_num = id_num + 1 #自增
                            else:

                                # 如果，该节点名称在集合中，则提取其原来的id,不用添加
                                #kg_node.append({"id": n_dict.get(kgnode[j]), "name": kgnode[j], "symbolSize": 30, "category": id_num})
                                pass
                            # 添加关系，源节点到目的节点
                            if j > 0:

                                kgrel[j - 1] = str(kgrel[j - 1]).strip()
                                kg_links.append({"name": kgrel[j - 1], "source": n_dict.get(kgnode[j - 1]),
                                                 "target": n_dict.get(kgnode[j])})
                            #
                #添加节点标签，分类; 先不分类有多少节点就几个分类
                #for k in n_set:
                #   kg_categories.append({"name": k})
                for k in n_dict.keys():
                    kg_categories.append({"name": k})
                context = {"nodes": kg_node, "links": kg_links, "categories": kg_categories}
                print(json.dumps(context, ensure_ascii=False))
            # 解析请求的json格式入参
        except Exception as why:
            print(why.args)
        else:
            content={'res':context}
            #处理逻辑
            print(data)
            # 返回自定义请求内容content,200状态码
            #response.headers["Access-Control-Allow-Origin"] = "http://127.0.0.1:80"
            return JsonResponse(data=content,status=status.HTTP_200_OK,content_type='application/json')
        # 如果不是post 请求返回不支持的请求方法
        return HttpResponseNotAllowed(permitted_methods=['POST'])

def show(request):
    return render(request, 'index.html')