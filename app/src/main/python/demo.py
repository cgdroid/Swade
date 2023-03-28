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

def graph1():
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

def graph2():
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

def graph3():
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

def graph4():
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


def main():
    dir = '../res/raw'
    path = f'{dir}/train.csv'
    file = join(dirname(__file__), 'train.csv')
    df = pd.read_csv(file)
    fig = df.describe()
#     plt.subplots(figsize = (12,4))
    count_plot = sns.countplot(x = 'Age', hue = 'Attrition', data = df, palette = 'colorblind')
    count_fig = count_plot.get_figure()
    filename = join(os.environ["HOME"], "count_plot.png")
    count_fig.savefig(filename)

#     df.head(7)
#     df.info()
#     count_plot = sns.countplot(df['Attrition'])
#     count_fig = count_plot.get_figure()
# # Error caused by writing a file to a directly which is read-only
# #     count_fig.savefig('count_plot.png')
# # Use this instead
#     filename = join(os.environ["HOME"], "count_plot.png")
#     count_fig.savefig(filename)
#     print(filename)

