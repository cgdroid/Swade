import numpy as np
import pandas as pd
import seaborn as sns
import cv2
from PIL import Image
import io
import base64
import matplotlib.pyplot as plt
import os
from os.path import dirname, join

def libs():
    fig = plt.figure()
    lst = [['apple', 'red', 11], ['grape', 'green', 22], ['orange', 'orange', 33], ['mango', 'yellow', 44]]
    df = pd.DataFrame(lst, columns =['Fruits', 'Color', 'Value'], dtype = float)
    count_plot = sns.countplot(df['Color'])
#     count_fig = count_plot.get_figure()
#     x_data = ['0.3', '5', '2', '10']
#     y_data = ['5', '-1', '4', '20']
#     ay = fig.add_subplot(1.5, 1, 1)
#     ay.plot(x_data, y_data)
    fig.canvas.draw()
    img = np.fromstring(fig.canvas.tostring_rgb(), dtype = np.uint8, sep = '')
    img = img.reshape(fig.canvas.get_width_height()[::-1] + (3,))
    img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)
    pil_im = Image.fromarray(img)
    output = io.BytesIO()
    pil_im.save(output, format = "PNG")
    img_str = base64.b64encode(output.getvalue())
    return "" + str(img_str, 'utf-8')

def get_raw_attrition():
    fig = plt.figure()
    filename = join(dirname(__file__), "train.csv")
    df = pd.read_csv(filename)
    df = df.drop('Over18', axis = 1)
    df = df.drop('EmployeeNumber', axis = 1)
    df = df.drop('StandardHours', axis = 1)
    df = df.drop('EmployeeCount', axis = 1)
    sns.countplot(df['Attrition'])
    fig.canvas.draw()
    img = np.fromstring(fig.canvas.tostring_rgb(), dtype = np.uint8, sep = '')
    img = img.reshape(fig.canvas.get_width_height()[::-1] + (3,))
    img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)
    pil_im = Image.fromarray(img)
    output = io.BytesIO()
    pil_im.save(output, format = "PNG")
    img_str = base64.b64encode(output.getvalue())
    return "" + str(img_str, 'utf-8')

def get_raw_count_plot(category):
    fig = plt.figure()
    filename = join(dirname(__file__), "train.csv")
    df = pd.read_csv(filename)
    df = df.drop('Over18', axis = 1)
    df = df.drop('EmployeeNumber', axis = 1)
    df = df.drop('StandardHours', axis = 1)
    df = df.drop('EmployeeCount', axis = 1)
    sns.countplot(x = category, hue = 'Attrition', data = df, palette = 'colorblind')
    fig.canvas.draw()
    img = np.fromstring(fig.canvas.tostring_rgb(), dtype = np.uint8, sep = '')
    img = img.reshape(fig.canvas.get_width_height()[::-1] + (3,))
    img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)
    pil_im = Image.fromarray(img)
    output = io.BytesIO()
    pil_im.save(output, format = "PNG")
    img_str = base64.b64encode(output.getvalue())
    return "" + str(img_str, 'utf-8')

def get_raw_heatmap():
    fig = plt.figure()
    filename = join(dirname(__file__), "train.csv")
    df = pd.read_csv(filename)
    df = df.drop('Over18', axis = 1)
    df = df.drop('EmployeeNumber', axis = 1)
    df = df.drop('StandardHours', axis = 1)
    df = df.drop('EmployeeCount', axis = 1)
    sns.heatmap(df.corr(), annot = True, fmt = '.0%')
    fig.canvas.draw()
    img = np.fromstring(fig.canvas.tostring_rgb(), dtype = np.uint8, sep = '')
    img = img.reshape(fig.canvas.get_width_height()[::-1] + (3,))
    img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)
    pil_im = Image.fromarray(img)
    output = io.BytesIO()
    pil_im.save(output, format = "PNG")
    img_str = base64.b64encode(output.getvalue())
    return "" + str(img_str, 'utf-8')

def main(data):
    x = data.splitlines()
    for line in x:
        y = x.split(",")


