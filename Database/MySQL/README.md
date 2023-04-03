# 数据库设计

需要存储的三元组内容在 [triplet.xlsx](./triplet.xlsx) 文件中，其中部分数据内容如下：

| 实体1 | 实体1标签 | 关系1 | 关系1标签 | 实体2 | 实体2标签 |
| --- | --- | --- | --- | --- | --- |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 帝王 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 将相 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 政治家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 军事家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 思想家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 史学家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 文学家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 科学家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 数学家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 书法家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 探险家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 医学家 | 人物概念 |
| 历史人物 | 人物概念 | 包含 | 下级概念 | 航天员 | 人物概念 |

## 表格分析

可以看到表格由三部分组成：

- 实体1
  - 实体名字
  - 实体标签
- 关系1
  - 关系名字
  - 关系标签
- 实体2
  - 实体名字
  - 实体标签

实体和实体标签之间以及关系和关系标签都不是一对一的。因此设计起来比较简单：

- 将实体归类成一类，并由 `name` 和 `label` 组成，然后由 `id` 作为主键。
- 将关系归类成一类，并由 `name` 和 `label` 组成，然后由 `id` 作为主键，同时将 `entity1_id` 和 `entity2_id` 作为外键约束。

## 额外考虑

因为要做成类似于图数据库的图谱查询类型，因此还需要判断实体是否作为 `entity1` 连接其他实体，所以我们还需要在实体的表中增加一个数据：

- 实体
  - 实体名字
  - 实体标签
  - 是否关联其他实体

## 表格构建

以下是创建 `graphview` 数据库、实体表和三元组表的 SQL 脚本示例：

```sql
CREATE DATABASE IF NOT EXISTS `graphview`;

USE `graphview`;

-- 创建关系表
CREATE TABLE IF NOT EXISTS `relation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `label` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建实体表
CREATE TABLE IF NOT EXISTS `entity` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `label` VARCHAR(255) NOT NULL,
  `has_entity1_relation` TINYINT(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建实体关系表
CREATE TABLE IF NOT EXISTS `entity_relation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `entity1_id` INT UNSIGNED NOT NULL,
  `relation_id` INT UNSIGNED NOT NULL,
  `entity2_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `entity1_id` (`entity1_id`),
  KEY `relation_id` (`relation_id`),
  KEY `entity2_id` (`entity2_id`),
  CONSTRAINT `entity_relation_ibfk_1` FOREIGN KEY (`entity1_id`) REFERENCES `entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `entity_relation_ibfk_2` FOREIGN KEY (`relation_id`) REFERENCES `relation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `entity_relation_ibfk_3` FOREIGN KEY (`entity2_id`) REFERENCES `entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## 其他

如果您不懂如何操作插入数据，可以参考我的 Python 脚本，并从 [triplet.xlsx](./triplet.xlsx) 中抽取数据并填充进 MySQL 数据库中。请注意自行 设定您的服务器设置。
大型数据可以使用 `ORM` 框架并分批操作：

```python
import pandas as pd
from sqlalchemy import create_engine, Column, Integer, String, ForeignKey
from sqlalchemy.orm import sessionmaker, relationship
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Entity(Base):
    __tablename__ = 'entity'

    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(255))
    label = Column(String(255))
    entity_relations = relationship("EntityRelation", back_populates="entities")

class Relation(Base):
    __tablename__ = 'relation'

    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(255))
    label = Column(String(255))
    entity_relations = relationship("EntityRelation", back_populates="relations")

class EntityRelation(Base):
    __tablename__ = 'entity_relation'

    id = Column(Integer, primary_key=True, autoincrement=True)
    entity1_id = Column(Integer, ForeignKey('entity.id'))
    relation_id = Column(Integer, ForeignKey('relation.id'))
    entity2_id = Column(Integer, ForeignKey('entity.id'))
    entities = relationship("Entity", back_populates="entity_relations")
    relations = relationship("Relation", back_populates="entity_relations")

df = pd.read_excel('./待转换三元组.xlsx')
df = df.fillna(value='NULL')

engine = create_engine('mysql+pymysql://root:849919718@localhost:3306/Huazhi?charset=utf8mb4')
Session = sessionmaker(bind=engine)
session = Session()

batch_size = 1000
num_batches = len(df) // batch_size + 1

for i in range(num_batches):
    start = i * batch_size
    end = (i + 1) * batch_size if (i + 1) * batch_size < len(df) else len(df)
    batch_df = df[start:end]

    entity_relation_list = []

    for index, row in batch_df.iterrows():
        entity1 = row['实体1']
        entity1_label = row['实体1标签']
        entity2 = row['实体2']
        entity2_label = row['实体2标签']
        relation = row['关系1']
        relation_label = row['关系1标签']

        # 使用UPSERT语句插入实体、关系和实体关系
        insert_sql = f"""
            INSERT INTO entity (name, label) VALUES ('{entity1}', '{entity1_label}')
            ON DUPLICATE KEY UPDATE name='{entity1}', label='{entity1_label}';
            INSERT INTO entity (name, label) VALUES ('{entity2}', '{entity2_label}')
            ON DUPLICATE KEY UPDATE name='{entity2}', label='{entity2_label}';
            INSERT INTO relation (name, label) VALUES ('{relation}', '{relation_label}')
            ON DUPLICATE KEY UPDATE name='{relation}', label='{relation_label}';
            INSERT INTO entity_relation (entity1_id, relation_id, entity2_id)
            SELECT e1.id, r.id, e2.id FROM entity e1, entity e2, relation r
            WHERE e1.name='{entity1}' AND e2.name='{entity2}' AND r.name='{relation}'
            ON DUPLICATE KEY UPDATE entity1_id=VALUES(entity1_id), relation_id=VALUES(relation_id), entity2_id=VALUES(entity2_id)
        """
        result = session.execute(insert_sql)
        entity_relation_list.append(result.lastrowid)

    session.commit()

session.close()
```