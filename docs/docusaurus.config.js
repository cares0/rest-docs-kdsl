// @ts-check
// `@type` JSDoc annotations allow editor autocompletion and type checking
// (when paired with `@ts-check`).
// There are various equivalent ways to declare your Docusaurus config.
// See: https://docusaurus.io/docs/api/docusaurus-config

import {themes as prismThemes} from 'prism-react-renderer';

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'REST Docs KDSL',
  tagline: 'Simplify Spring REST Docs with Kotlin DSL',
  favicon: 'img/favicon.ico',

  // Set the production url of your site here
  url: 'https://cares0.github.io',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/rest-docs-kdsl',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'cares0', // Usually your GitHub org/user name.
  projectName: 'rest-docs-kdsl', // Usually your repo name.
  deploymentBranch: 'gh-pages',

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en', 'ko'],
    localeConfigs: {
      en: {
        htmlLang: 'en-GB'
      },
      ko: {
        direction: 'ltr'
      }
    }
  },

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: './sidebars.js',
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          // editUrl:
          //   'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
        },
        // blog: {
        //   showReadingTime: true,
        //   feedOptions: {
        //     type: ['rss', 'atom'],
        //     xslt: true,
        //   },
        //   // Please change this to your repo.
        //   // Remove this to remove the "edit this page" links.
        //   editUrl:
        //     'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
        //   // Useful options to enforce blogging best practices
        //   onInlineTags: 'warn',
        //   onInlineAuthors: 'warn',
        //   onUntruncatedBlogPosts: 'warn',
        // },
        theme: {
          customCss: './src/css/custom.css',
        },
      }),
    ],
  ],
  themeConfig:
  /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
      ({
        // Replace with your project's social card
        // image: 'img/docusaurus-social-card.jpg',
        navbar: {
          title: 'REST Docs KDSL',
          // logo: {
          //   alt: 'REST Docs KDSL',
          //   src: 'https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original.svg',
          // },
          items: [
            {
              type: 'docSidebar',
              sidebarId: 'tutorialSidebar',
              position: 'left',
              label: 'Docs',
            },
            {
              type: 'docsVersionDropdown',
              sidebarId: 'docsVersionDropdown',
              position: 'left',
              label: 'Version',
            },
            {
              href: 'https://github.com/cares0/rest-docs-kdsl',
              label: 'GitHub',
              position: 'right',
            },
            {
              type: 'localeDropdown',
              position: 'right',
            },
          ],
        },
        footer: {
          style: 'dark',
          links: [
            // {
            //   title: 'Docs',
            //   items: [
            //     {
            //       label: 'Tutorial',
            //       to: '/docs/intro',
            //     },
            //   ],
            // },
            {
              title: 'More',
              items: [
                {
                  label: 'GitHub',
                  href: 'https://github.com/cares0',
                },
                {
                  label: 'Blog',
                  href: 'https://cares-log.tistory.com/',
                },
              ],
            },
            {
              title: 'Contact',
              items: [
                {
                  label: 'Email',
                  href: 'mailto:cares00000@gmail.com',
                },
              ],
            },
          ],
          copyright: `Copyright © ${new Date().getFullYear()} YoungJun Kim. Built with Docusaurus.`,
        },
        prism: {
          theme: prismThemes.github,
          darkTheme: prismThemes.dracula,
        },
      }),
  headTags: [
    {
      tagName: 'meta',
      attributes: {
        name: 'google-site-verification',
        content: 'QsEzQT9h2oy4eEo8LmlJguhlEYBogNZ3DQEA-IsVvcY'
      }
    }
  ]
};

export default config;
