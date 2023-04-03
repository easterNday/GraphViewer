import pandas as pd
from py2neo import Graph, Node, Relationship
from tqdm import tqdm

# 读取 Excel 文件
df = pd.read_excel('../Data/triplet.xlsx')

# 连接到 Neo4j 数据库
graph = Graph("bolt://dev.easternday.top:7687", auth=("neo4j", "849919718"))

# 开始事务
tx = graph.begin()

# 遍历 DataFrame 并创建实体和关系
for index, row in tqdm(df.iterrows(), total=df.shape[0]):
    entities = {}
    for i in [1, 2]:
        entity_name = row[f'实体{i}']
        entity = Node('Entity', name=entity_name)
        entities[i] = entity
        tx.merge(entity, 'Entity', 'name')

    relation_name = row['关系1']
    relation_label = row['关系1标签']
    entity1_name = row['实体1']
    entity1_label = row['实体1标签']
    entity2_name = row['实体2']
    entity2_label = row['实体2标签']
    relation = Relationship(entities[1], relation_name,
                            entities[2], label=relation_label)
    relation['entity1_label'] = entity1_label
    relation['entity2_label'] = entity2_label
    tx.merge(relation)

# 提交事务
graph.commit(tx)