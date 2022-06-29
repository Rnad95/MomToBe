{
  "api": {
    "momtobe": {
      "service": "AppSync",
      "providerPlugin": "awscloudformation",
      "dependsOn": [],
      "output": {
        "authConfig": {
          "defaultAuthentication": {
            "authenticationType": "API_KEY",
            "apiKeyConfig": {
              "apiKeyExpirationDays": 100,
              "apiKeyExpirationDate": "2022-10-05T09:28:43.195Z",
              "description": "jgjgj"
            }
          },
          "additionalAuthenticationProviders": []
        }
<<<<<<< HEAD
    },
    "auth": {
        "momtobe40d2e53d": {
            "IdentityPoolId": "string",
            "IdentityPoolName": "string",
            "UserPoolId": "string",
            "UserPoolArn": "string",
            "UserPoolName": "string",
            "AppClientIDWeb": "string",
            "AppClientID": "string",
            "CreatedSNSRole": "string"
        }
    },
    "storage": {
        "s302dc1ff1": {
            "BucketName": "string",
            "Region": "string"
        }
    },
    "predictions": {
        "speechGenerator5bbede17": {
            "region": "string",
            "language": "string",
            "voice": "string"
        }
=======
      }
>>>>>>> 0dd3aea9f3bd9bf8590ae52f5654ef47538d5343
    }
  },
  "auth": {
    "momtobe40d2e53d": {
      "service": "Cognito",
      "providerPlugin": "awscloudformation",
      "dependsOn": [],
      "customAuth": false,
      "frontendAuthConfig": {
        "socialProviders": [],
        "usernameAttributes": [
          "EMAIL"
        ],
        "signupAttributes": [
          "EMAIL"
        ],
        "passwordProtectionSettings": {
          "passwordPolicyMinLength": 8,
          "passwordPolicyCharacters": []
        },
        "mfaConfiguration": "OFF",
        "mfaTypes": [
          "SMS"
        ],
        "verificationMechanisms": [
          "EMAIL"
        ]
      }
    }
  },
  "storage": {
    "s302dc1ff1": {
      "service": "S3",
      "providerPlugin": "awscloudformation",
      "dependsOn": []

    },
    "s302dc1ff1": {
      "service": "S3",
      "providerPlugin": "awscloudformation",
      "dependsOn": []
    }
  },
  "predictions": {
    "speechGenerator5bbede17": {
      "providerPlugin": "awscloudformation",
      "service": "Polly",
      "convertType": "speechGenerator"

    }
  },
  "predictions": {
    "speechGenerator5bbede17": {
      "providerPlugin": "awscloudformation",
      "service": "Polly",
      "convertType": "speechGenerator"
    }
  }
}