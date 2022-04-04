[install gcloud cli](https://cloud.google.com/sdk/docs/install)
then run `gcloud auth login`

[read me first](https://cloud.google.com/resource-manager/docs/creating-managing-projects#before_you_begin)

```shell
# create a project
export PROJECT_ID=example-$RANDOM
gcloud projects create "$PROJECT_ID"
gcloud config set project "$PROJECT_ID"
```

you should see `Operation [...] finished successfully`

To use all the nice things here, you need
to [enable billing](https://cloud.google.com/billing/docs/how-to/modify-project#enable_billing_for_a_project), google
offers a lot of free usage in the first year, but you have to leave your credit card info. For financial safety, create
a [budget alert](https://cloud.google.com/billing/docs/how-to/budgets#create-budget) so you get notified if anything is
producing a bill.

[read me first](https://cloud.google.com/build/docs/automating-builds/build-repos-from-github#before_you_begin)
then fork this starter project

```shell
export GH_USER="your github username"
gcloud beta builds triggers create github \
        --build-config="/.google/cloudbuild.yaml" \
        --repo-name=serenity-junit-screenplay-starter \
        --repo-owner="$GH_USER" \
        --name=example-trigger \
        --branch-pattern='^main$'        
```

running will fail with

```
ERROR: (gcloud.beta.builds.triggers.create.github) FAILED_PRECONDITION: Repository mapping does not exist. Please visit https://console.cloud.google.com/cloud-build/triggers/connect?project=589211296888 to connect a repository to your project
```

follow the url to connect gcloud with your github account

Running the command now, you should see `Operation [...] finished successfully.`

create a bucket to store test results... unfortunately no convenient CLI for that, so go
to https://console.cloud.google.com/storage/create-bucket to create one like
![here](./createbucket.png)
and then change the artifacts: location in [the cloudbuild file](./cloudbuild.yaml)

#### FIXME you should be able to run a cloudbuid with gsutils to create that bucket

```shell
# command requires a source passed as argument, use empty dir
mkdir /tmp/empty
 gcloud builds submit --config .google/cloudbuild-create-bucket-to-store-report.yaml /tmp/empty/
```

#### FIXME enable container registry, use cloudbuild to pull and store a selenium grid docker image.

make a change and push it

```shell
echo " " >> .google/README.md
git add .google/README.md
git commit -m "whitespace commit to trigger build"
git push origin main
```