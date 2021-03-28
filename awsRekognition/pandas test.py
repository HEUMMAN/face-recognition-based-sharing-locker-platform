import csv
import pandas as pd
account = pd.read_csv('new_user_credentials.csv')
accessKeyId = account['Access key ID']
secretAccessKey = account['Secret access key']
#print(accessKeyId)
#print(secretAccessKey)
#print(account.iloc[:,[2,2]])
a = account.iloc[:,[2,2]]
print(account['Secret access key'][0])
'''
lines = account.readline()
accessKeyId = lines[2]
print(accessKeyId)
'''