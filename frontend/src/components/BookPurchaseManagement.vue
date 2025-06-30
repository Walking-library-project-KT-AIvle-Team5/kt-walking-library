<template>
    <div>
        <Number
            label="PurchaseRequestId"
            v-model="value.purchaseRequestId"
            :editMode="editMode"
        />
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <String
            label="BookId"
            v-model="value.bookId"
            :editMode="editMode"
        />
        <Number
            offline
            label="Price"
            v-model="value.price"
            :editMode="editMode"
            @change="change"
        />
        <Number
            offline
            label="Point"
            v-model="value.point"
            :editMode="editMode"
            @change="change"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'BookPurchaseManagement',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "bookPurchaseManagements",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
