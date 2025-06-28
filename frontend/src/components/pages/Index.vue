<template>
  <v-container fluid>
    <v-row>
      <v-card
          v-for="(card, index) in cards"
          :key="index"
          class="mx-auto"
          style="height:300px; width:300px; margin-bottom:20px;"
          outlined
      >
        <v-list-item>
          <v-icon style="margin-top: 30%; margin-left: 37.5%;" color="primary" size="64">mdi-apps</v-icon>
        </v-list-item>
        <v-card-actions>
          <v-btn
              class="mx-auto"
              outlined
              rounded
              :to="card.link"
              style="font-weight:500; font-size:20px; border:solid 2px; max-width:250px; overflow:hidden; margin-top: 10%;"
          >
              {{ card.text }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Index',
  data: () => ({
    cards: [
      {
          text: "회원관리",
          link: "/memberManagements",
      },
      {
          text: "포인트 조회",
          link: "/pointViews",
      },
      {
          text: "구독자 목록",
          link: "/fetchSubscriberLists",
      },
      {
          text: "통계 열람",
          link: "/authorStatistics",
      },
      {
          text: "도서",
          link: "/books",
      },
      {
          text: "원고",
          link: "/manuscripts",
      },
      {
          text: "포인트",
          link: "/points",
      },
      {
          text: "구독관리",
          link: "/subscribeManagements",
      },
      {
          text: "독서관리",
          link: "/readingManagements",
      },
      {
          text: "포인트 요청 관리",
          link: "/pointRequestManagements",
      },
      {
          text: "도서 구매 관리",
          link: "/bookPurchaseManagements",
      },
      {
          text: "도서 출간",
          link: "/bookPublications",
      },
      {
          text: "플랫폼 관리",
          link: "/platformManagements",
      },
      {
          text: "작가",
          link: "/authorRegistrations",
      },
    ],
  }),
};
</script>
