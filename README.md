#                                                     Statistic and Probability Project Report
Abdussamet KACI                                                                                                            14.05.2020
### Subject: Hypothesis testing (Z-test for difference proportions)
In this project, I studied about whether or not a hypothesis can be accepted. We have two samples in a population. We have two proportion and two count from these samples. The null hypothesis is proportion A (pA) equals proportion B (pB) and we will test this hypothesis with three alternative hypothesis; left tail, right tail, two sided. Also, we decide how many percent we will do this test and we will use significance level. And, we calculate the value of test statistic (Z) with formulas. We find the critical value (   from standard normal distribution table.

We estimate the common proportion by the overall proportion of defective parts: 

![image](https://user-images.githubusercontent.com/61049743/94153798-e2f47b00-fe85-11ea-9016-9d38f7fffcd1.png)

The test statistic is calculated from this formula: 

![image](https://user-images.githubusercontent.com/61049743/94153973-12a38300-fe86-11ea-873d-798c061cf82c.png)

In the right tail, if test statistic is equal or more than critical value [![image](https://user-images.githubusercontent.com/61049743/94154205-55fdf180-fe86-11ea-979f-80743998095c.png), the null hypothesis is rejected. Otherwise, the null hypothesis is accepted.  

In the left tail, if test statistic is equal or less than critical value (-![image](https://user-images.githubusercontent.com/61049743/94154438-8ba2da80-fe86-11ea-802a-10533930a8b5.png)], the null hypothesis is rejected. Otherwise, the null hypothesis is accepted.  

In the two sided, for deciding to reject or accept the null hypothesis, two regions are observed. If the test statistic is between (-![image](https://user-images.githubusercontent.com/61049743/94154705-dae90b00-fe86-11ea-9d0d-b9b4c401f3db.png)] or [![image](https://user-images.githubusercontent.com/61049743/94154882-066bf580-fe87-11ea-8417-8695e2ee9677.png) the null hypothesis is rejected. Otherwise, the null hypothesis is accepted  

![image](https://user-images.githubusercontent.com/61049743/94155199-5c409d80-fe87-11ea-8def-176c22ea7f2e.png)

### About My GUI
My GUI is like below. Proportions and its count are taken from user. The user can select what user want alternative hypothesis method; left-tail, right tail or two sided.  When the user push draw button, acceptance or rejection regions are filling. Acceptance regions are filling with green color and rejection regions are filling red color.

![image](https://user-images.githubusercontent.com/61049743/94155377-8f832c80-fe87-11ea-9455-a76d0fa9857d.png)

Furthermore, for giving some information to users, it is showed a message box. It is indicated that the users’ hypothesis is accepted or rejected. The value of test statistic and the test statistic are showed in which regions.

![image](https://user-images.githubusercontent.com/61049743/94155517-b3467280-fe87-11ea-8995-b092ca28fdb2.png)
