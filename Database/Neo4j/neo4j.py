import pandas as pd
from py2neo import Graph, Node, Relationship
from tqdm import tqdm

# 读取 Excel 文件
df = pd.read_excel('../Data/triplet.xlsx')

# 连接到 Neo4j 数据库
graph = Graph("bolt://dev.easternday.top:7687", auth=("neo4j", "password"))

# 清空数据库
# graph.delete_all()

# 开始事务
tx = graph.begin()

# 清空数据库
tx.run("MATCH (n) DETACH DELETE n")

# 遍历 DataFrame 并创建实体和关系
for index, row in tqdm(df.iterrows(), total=df.shape[0]):
    entities = {}
    for i in [1, 2]:
        entity_name = row[f'实体{i}']
        entity = Node("entity", name=entity_name)
        entities[i] = entity
        tx.merge(entity, 'entity', 'name')

    relation_name = row['关系1']
    relation_label = row['关系1标签']
    entity1_name = row['实体1']
    entity1_label = row['实体1标签']
    entity2_name = row['实体2']
    entity2_label = row['实体2标签']
    relation = Relationship(entities[1], "relation", entities[2])
    relation['entity1_label'] = entity1_label
    relation['entity2_label'] = entity2_label
    relation['name'] = relation_name
    relation['label'] = relation_label
    tx.merge(relation)

# 提交事务
graph.commit(tx)
