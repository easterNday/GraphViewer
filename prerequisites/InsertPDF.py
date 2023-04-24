import pymysql.cursors
import glob
import os
from PyPDF2 import PdfReader
from tqdm import tqdm

# MySQL 连接信息
HOST = 'dev.easternday.top'
USER = 'root'
PASSWORD = '849919718'
DB = 'content'

# 定义上传函数
def upload_file_to_mysql(pdf_file_path, pdf_name, conn):
    with open(pdf_file_path, 'rb') as file:
        pdf_data = file.read()
        num_pages = len(PdfReader(file).pages)
    try:
        with conn.cursor() as cursor:
            cursor.execute("REPLACE INTO pdf_fileinfo (pdf_name, pdf_file, page_count) VALUES (%s, %s, %s)", (pdf_name, pdf_data, num_pages))
        conn.commit()
    except:
        conn.rollback()
        raise

if __name__ == '__main__':
    # 连接到 MySQL 服务器
    conn = pymysql.connect(host=HOST, user=USER, password=PASSWORD, db=DB, charset='utf8mb4')

    # 遍历指定文件夹下所有 PDF 文件并上传
    pdf_files = glob.glob(os.path.join(os.getcwd(), "三常语料", "*.pdf"))
    for pdf_file_path in tqdm(pdf_files):
        pdf_name = os.path.splitext(os.path.basename(pdf_file_path))[0]
        upload_file_to_mysql(pdf_file_path, pdf_name, conn)

    # 关闭数据库连接
    conn.close()