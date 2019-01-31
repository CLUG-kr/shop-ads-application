import requests
import time

from selenium.webdriver.common.by import By
import selenium
from selenium import webdriver
from bs4 import BeautifulSoup
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
def updateCU():
    session = requests.Session()
    newUrl = 'http://cu.bgfretail.com/event/plusAjax.do'
    count = 1
    returnDataCU = ''
    while True:
        payload = {
            'pageIndex': count,
            'listType:': 0,
            'searchCondition': None
        }
        response = session.post(newUrl, data=payload)

        soup = BeautifulSoup(response.text, 'html.parser')
        products = soup.select('ul>li')
        if str(products) == '[]':
            break;
        for x in products:
            temp = x.find('p', {'class': 'prodName'})
            if temp == None:
                returnDataCU += x.getText() + '#<h1>'
            else:
                price = x.find('p', {'class': 'prodPrice'})
                returnDataCU += '<h1>' + temp.getText() + '!' +  price.getText()+ '!'
        count += 1
        return returnDataCU


def updateGS25():
    sUrl = "http://gs25.gsretail.com/gscvs/ko/products/event-goods"
    options = webdriver.ChromeOptions()
    options.add_argument('headless')
    options.add_argument('window-size=1920x1080')
    options.add_argument("disable-gpu")
    driver = webdriver.Chrome('chromedriver', options = options)
    driver.implicitly_wait(3)
    driver.get(sUrl)
    prevEndCount = "prev"
    returnDataGS25 = ""
    while True:
        time.sleep(3)
        WebDriverWait(driver, 100).until(EC.invisibility_of_element_located((By.CLASS_NAME, "blockUI blockOverlay")))
        #driver.find_element_by_xpath('//*[ @ id = "TOTAL"]').click()

        el = driver.find_element_by_link_text("다음 페이지로 이동")
        WebDriverWait(driver, 100).until(EC.invisibility_of_element_located((By.CLASS_NAME, "blockUI blockOverlay")))
        time.sleep(1)
        count = 0
        while True:
            try:
                WebDriverWait(driver,100).until(EC.element_to_be_clickable((By.LINK_TEXT,"다음 페이지로 이동")))
                driver.execute_script("scroll(0, 1500)");
                time.sleep(1)
                print("t")
                driver.execute_script("arguments[0].click();", el)
                print(123)
                break;
            except selenium.common.exceptions.StaleElementReferenceException:
                count = count +1
                if count == 10:
                    print("error_update")
                    return "error"
                time.sleep(1)

        '''
        for x in linkList:
            if x.getAttribute("href").contains('#next;'):
                x.click()
                break
        '''

        getHTML = driver.page_source
        soup = BeautifulSoup(getHTML, 'html.parser')
        getData = soup.findAll('div', {'class' : 'prod_box'})

        endCount = getData[3].find('p',{'class':'tit'}).getText()
        if endCount == prevEndCount:
            break;
        else :
            prevEndCount = endCount
            print('pass')
        for x in getData[3:11]:
            print(x)
            returnDataGS25 += "<h1>"+x.find('p',{'class':'tit'}).getText() +"!"+ x.find('span').getText()+"!"+x.find('div').getText()+"#</h1>"
    driver.quit()
    return returnDataGS25