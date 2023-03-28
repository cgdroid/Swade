# Run the following code in another thread in a Java program
# e.g new Thread.start(<Runnable Object> running this program)

def say_hello():
    print('Hello there!')

def main():
    say_hello()
    while 1:
        print(">", end = "", flush = True)