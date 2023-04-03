# 示例 Python 脚本
import pandas as pd
import pymysql.cursors

# MySQL 连接信息
HOST = 'localhost'
USER = 'root'
PASSWORD = 'root'
DB = 'graphview'


def insert_data():
    conn = pymysql.connect(host=HOST, user=USER, password=PASSWORD, db=DB, charset='utf8mb4')
    cursor = conn.cursor()

    # 读取数据
    df = pd.read_excel('../Data/triplet.xlsx')

    # 将实体和关系插入到相应的表格中
    entities = set(df['实体1']).union(set(df['实体2']))
    for entity in entities:
        cursor.execute("""INSERT INTO `entity` (`name`, `label`) VALUES (%s, %s)""", (entity, 'person'))
    conn.commit()

    relations = set(df['关系1'])
    for relation in relations:
        cursor.execute("""INSERT INTO `relation` (`name`, `label`) VALUES (%s, %s)""", (relation, 'rel'))
    conn.commit()

    # 将三元组插入到实体关系表格中
    for index, row in df.iterrows():
        cursor.execute(
            """INSERT INTO `entity_relation` (`entity1_id`, `relation_id`, `entity2_id`)
            SELECT t1.id, t2.id, t3.id
            FROM `entity` AS t1, `relation` AS t2, `entity` AS t3
            WHERE t1.name = %s AND t2.name = %s AND t3.name = %s""",
            (row['实体1'], row['关系1'], row['实体2'])
        )
        conn.commit()

    cursor.close()
    conn.close()

if __name__ == '__main__':
    insert_data()