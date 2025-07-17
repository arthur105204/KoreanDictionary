# Korean Dictionary Android App

A comprehensive Android application for Korean dictionary lookup using the KRDict Open API. The app provides Korean word definitions, English translations, and example sentences with a modern Material Design interface.

## Features

- 🔍 **Korean Word Search**: Search for Korean words and get detailed definitions
- 🌐 **English Translations**: Get up to 10 English meanings per word
- 📝 **Example Sentences**: View Korean example sentences with usage
- 🎯 **POS Grouping**: Multiple meanings with the same part of speech are grouped together
- 📱 **Modern UI**: Material Design interface with cards and smooth animations
- 🔤 **Romanization**: Korean pronunciation in romanized form
- 🌍 **Multi-language Support**: English translations and interface

## Screenshots

*Add screenshots here when available*

## Setup Instructions

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK (API level 24+)
- KRDict Open API key

### 1. Clone the Repository

```bash
git clone https://github.com/arthur105204/KoreanDictionary.git
cd KoreanDictionary
```

### 2. Get Your API Key

1. Visit [KRDict Open API](https://krdict.korean.go.kr/openApi/openApiInfo)
2. Register for an account and request an API key
3. Wait for approval (usually takes 1-2 business days)

### 3. Configure API Key

**IMPORTANT**: Never commit your API key to version control!

1. Open `local.properties` in the project root
2. Add your API key:
```properties
# Your existing SDK path
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk

# Add your KRDict API key here
KR_DICT_API_KEY=YOUR_ACTUAL_API_KEY_HERE
```

### 4. Build and Run

1. Open the project in Android Studio
2. Sync Gradle files
3. Build the project
4. Run on an emulator or physical device

## Project Structure

```
app/src/main/java/vn/edu/fpt/koreandictionary/
├── adapter/
│   └── POSCardAdapter.java          # RecyclerView adapter for POS cards
├── model/
│   ├── DictionaryItem.java          # Main dictionary item model
│   ├── Example.java                 # Example sentence model
│   ├── ExampleItem.java             # Example API response item
│   ├── ExampleResponse.java         # Example API response
│   ├── KRDictResponse.java          # Main API response
│   ├── POSGroup.java                # POS grouping model
│   ├── Sense.java                   # Word sense/meaning model
│   └── Translation.java             # Translation model
├── network/
│   ├── ApiTestActivity.java         # API testing activity
│   ├── KRDictApiService.java        # Retrofit API interface
│   ├── RawApiTest.java              # Raw HTTP API test
│   └── RetrofitClient.java          # Retrofit client setup
├── util/
│   └── Resource.java                # Resource wrapper for LiveData
├── viewmodel/
│   └── DictionaryViewModel.java     # Main ViewModel
└── MainActivity.java                # Main activity
```

## API Integration

The app uses the KRDict Open API with the following endpoints:

- **Search**: `/api/search` - Get word definitions and translations
- **Examples**: `/api/search?part=exam` - Get example sentences

### Key API Parameters

- `key`: Your API key
- `q`: Search query (Korean word)
- `translated`: "y" for translations
- `trans_lang`: "1" for English
- `part`: "word" for definitions, "exam" for examples

## Dependencies

- **Retrofit 2.9.0**: HTTP client for API calls
- **SimpleXML 2.9.0**: XML parsing for API responses
- **KoreanRomanizer 2.0.1**: Korean to romanized text conversion
- **Material Design**: Modern UI components
- **ViewModel & LiveData**: Architecture components

## Architecture

The app follows MVVM (Model-View-ViewModel) architecture:

- **Model**: Data classes for API responses
- **View**: Activities and adapters
- **ViewModel**: Business logic and data management
- **Repository Pattern**: API service layer

## Security

- API key is stored in `local.properties` (not committed to Git)
- BuildConfig generates secure access to API key
- `.gitignore` excludes sensitive files

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [KRDict Open API](https://krdict.korean.go.kr/openApi/openApiInfo) for providing the dictionary data
- [KoreanRomanizer](https://github.com/crizin/KoreanRomanizer) for Korean romanization
- Android Material Design for UI components

## Support

If you encounter any issues:

1. Check that your API key is correctly configured
2. Ensure you have internet connectivity
3. Verify the API key has proper permissions
4. Check the logs for detailed error messages

## Changelog

### Version 1.0
- Initial release
- Korean word search functionality
- English translations
- Example sentences
- Material Design UI
- API key security implementation 